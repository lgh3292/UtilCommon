package com.lgh.util.dao;


import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import oracle.sql.BLOB;
import com.lgh.util.StreamUtil;
import com.lgh.util.logging.LogUtil;

/**  
 * This class is the abstract daoSupport,all daoImpl should extends this class
 * For that database columns' name is different from the Entity Object params' name,
 * we transfer fieldNames and the columnNames as parameters
 *
 * @author liuguohua
 */
public abstract class BaseDao {

    /**
     * batch commit data every BATCH_INSERT_COUNT*
     */
    protected final int BATCH_INSERT_COUNT = 1000;
    /**
     * validate int or long value is legal*
     */
    protected final int ILLEGAL_NUM = -1;

    protected boolean isLegal(Integer num) {
        return num == null ? false : num == ILLEGAL_NUM ? false : true;
    }

    protected boolean isLegal(Long num) {
        return num == null ? false : num == ILLEGAL_NUM ? false : true;
    }

    /**
     * return condition by keys for query by "in" for char
     *
     * @param keys
     * @return
     */
    protected StringBuffer getConditon_char(List<? extends Object> keys) throws Exception {
        if (null == keys || keys.size() == 0) {
            throw new Exception("keys could not be null!");
        }
        StringBuffer condition = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            Object key = keys.get(i);
            if (key != null) {
                if (i != keys.size() - 1) {
                    condition.append("'" + key.toString() + "'" + ",");
                } else {
                    condition.append("'" + key + "'");
                }
            }

        }
        return condition;
    }

    /**
     * return condition by keys for query by "in" for number
     *
     * @param keys
     * @return
     */
    protected StringBuffer getConditon(List<? extends Object> keys) throws Exception {
        if (null == keys || keys.size() == 0) {
            throw new Exception("keys could not be null!");
        }
        StringBuffer condition = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            Object key = keys.get(i);
            if (key != null) {
                if (i != keys.size() - 1) {
                    condition.append(key.toString() + ",");
                } else {
                    condition.append(key);
                }
            }

        }
        return condition;
    }

    /**
     * detect if the object is null
     *
     * @param object
     * @return
     */
    protected boolean isNull(Object object) {
        return object == null ? true : false;
    }

    /**
     * detect if the str is null or trim(str) is equals ""
     *
     * @param str
     * @return
     */
    protected boolean nullOrTrimNull(String str) throws Exception {
        if (str != null) {
            if (!str.trim().equals("")) {
                if (str.trim().contains("'")) {
                    throw new Exception("invalid parameter,it could not contains \"'\"!");
                }
                return false;
            }
        }
        return true;
    }


    /**
     * save object base on the Class type,if the object is entity then go to insertEntity
     * if it's Collection then go to batchInsertEntity else throw Exception
     *
     * @param con
     * @param entity
     * @param cls
     * @param sqlStr
     * @param fieldNames
     * @throws Exception
     * @throws SQLException
     * @throws Exception
     */
    protected void save(Connection con, Object entity, Class<?> cls, String sqlStr, String[] fieldNames) throws Exception, SQLException, Exception {
        if (entity.getClass() == cls) {
            insertEntity(con, sqlStr, fieldNames, entity);
        } else if (entity instanceof Collection<?>) {
            batchInsertEntity(con, sqlStr, fieldNames, (Collection<?>) entity, cls, BATCH_INSERT_COUNT);
        } else {
            throw new Exception(entity.getClass() + " is not the classType " + cls);
        }
    }

    /**
     * update database data by primaryKey.
     *
     * @param con
     * @param entity
     * @param cls
     * @param sqlStr
     * @param fieldNames
     * @throws Exception
     * @throws SQLException
     * @throws Exception
     */
    protected void update(Connection con, Object entity, Class<?> cls, String sqlStr, String[] fieldNames) throws Exception, SQLException, Exception {
        if (entity.getClass() == cls) {
            updateEntity(con, sqlStr, fieldNames, entity);
        } else if (entity instanceof Collection<?>) {
            batchUpdateEntity(con, sqlStr, fieldNames, (Collection<?>) entity, cls, BATCH_INSERT_COUNT);
        } else {
            throw new Exception(entity.getClass() + " is not the classType " + cls);
        }
    }

    /**
     * delete data by sql key
     *
     * @param con
     * @param entity
     * @param cls
     * @param sqlStr
     * @param fieldNames
     * @throws Exception
     * @throws SQLException
     * @throws Exception
     */
    protected void delete(Connection con, Object entity, Class<?> cls, String sqlStr, String[] fieldNames) throws Exception, SQLException, Exception {
        if (entity.getClass() == cls) {
            deleteEntity(con, sqlStr, fieldNames, entity);
        } else if (entity instanceof Collection<?>) {
            batchDeleteEntity(con, sqlStr, fieldNames, (Collection<?>) entity, cls, BATCH_INSERT_COUNT);
        } else {
            throw new Exception(entity.getClass() + " is not the classType " + cls);
        }
    }

    /**
     * batch commit the data
     *
     * @param con
     * @param sql
     * @param fieldNames
     * @param collection
     * @param cls
     * @param batChCount
     * @throws Exception
     * @throws SQLException
     * @throws Exception
     */
    protected void batchInsertEntity(Connection con, String sql, String[] fieldNames, Collection<?> collection, Class<?> cls, int batChCount) throws Exception, SQLException, Exception {
        PreparedStatement pst = null;
        try {
        	long start = System.currentTimeMillis();
            pst = con.prepareStatement(sql);
            int i = 0;
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object.getClass() == cls) {
                    invoke(pst, fieldNames, object);
                    pst.addBatch();
                    i++;
                    if (i % batChCount == 0 && i > 0) {
                        pst.executeBatch();
                        pst.clearBatch();
                    }
                } else {
                    throw new Exception(object.getClass() + " is not the classType " + cls);
                }
            }
            pst.executeBatch();
            pst.clearBatch();
            LogUtil.info("_______batch insert to db count:"+collection.size()+"  wasteTime:"+(System.currentTimeMillis()-start));
        } finally {
            closeStream(pst);
        }
    }


    /**
     * insert an entity object
     *
     * @param sql
     * @param fieldNames
     * @param object
     * @return
     * @throws SQLException
     * @throws Exception
     */
    protected void insertEntity(Connection con, String sql, String[] fieldNames, Object object) throws SQLException, Exception {
        PreparedStatement pst = null;
        LogUtil.debug("insert " + object.getClass().getName() + " SQL:" + sql);
        try {
            pst = con.prepareStatement(sql);
            invoke(pst, fieldNames, object);
            pst.executeUpdate();
        } finally {
            closeStream(pst);
        }
    }


    /**
     * update an entity object
     *
     * @param sql
     * @param fieldNames
     * @param object
     * @return
     * @throws SQLException
     * @throws Exception
     */
    protected void updateEntity(Connection con, String sql, String[] fieldNames, Object object) throws SQLException, Exception {
        PreparedStatement pst = null;
        LogUtil.debug("update " + object.getClass().getName() + " SQL:" + sql);
        try {
            pst = con.prepareStatement(sql);
            invoke(pst, fieldNames, object);
            pst.executeUpdate();
        } finally {
            closeStream(pst);
        }
    }

    /**
     * batch update the entity objects
     *
     * @param sql
     * @param fieldNames
     * @param object
     * @return
     * @throws Exception
     * @throws SQLException
     * @throws Exception
     */
    protected void batchUpdateEntity(Connection con, String sql, String[] fieldNames, Collection<?> collection, Class<?> cls, int batChCount) throws Exception, SQLException, Exception {
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(sql);
            int i = 0;
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object.getClass() == cls) {
                    invoke(pst, fieldNames, object);
                    pst.addBatch();
                    if (i % batChCount == 0 && i > 0) {
                        pst.executeBatch();
                        pst.clearBatch();
                    }
                    i++;
                } else {
                    throw new Exception(object.getClass() + " is not the classType " + cls);
                }
            }
            pst.executeBatch();
            pst.clearBatch();
        } finally {
            closeStream(pst);
        }
    }

    /**
     * delete entity object
     *
     * @param sql
     * @param fieldNames
     * @param object
     * @throws DatabaseDownException
     * @throws SQLException
     * @throws Exception
     */
    protected void deleteEntity(Connection con, String sql, String[] fieldNames, Object object) throws SQLException, Exception {
        PreparedStatement pst = null;
        LogUtil.debug("delete " + object.getClass().getName() + " SQL:" + sql);
        try {
            pst = con.prepareStatement(sql);
            invoke(pst, fieldNames, object);
            pst.executeUpdate();
        } finally {
            closeStream(pst);
        }
    }


    /**
     * delete entity object
     *
     * @param sql
     * @param fieldNames
     * @param object
     * @throws Exception
     * @throws SQLException
     * @throws Exception
     */
    protected void batchDeleteEntity(Connection con, String sql, String[] fieldNames, Collection<?> collection, Class<?> cls, int batChCount) throws Exception, SQLException, Exception {
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(sql);
            int i = 0;
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object.getClass() == cls) {
                    invoke(pst, fieldNames, object);
                    pst.addBatch();
                    if (i % batChCount == 0 && i > 0) {
                        pst.executeBatch();
                        pst.clearBatch();
                    }
                    i++;
                } else {
                    throw new Exception(object.getClass() + " is not the classType " + cls);
                }
            }
            pst.executeBatch();
            pst.clearBatch();
        } finally {
            closeStream(pst);
        }
    }


    /**
     * find object
     *
     * @param sql
     * @param columns
     * @param fieldNames
     * @param entity
     * @return
     * @throws SQLException
     */
    protected List<Object> find(Connection con, String sql, String[] columns, String[] fieldNames, Class<?> cls) throws SQLException, Exception {
        PreparedStatement pst = null;
        ResultSet rsltSet = null;
        try {
            List<Object> list = new ArrayList<Object>();
            LogUtil.debug("select  " + cls.getName() + " SQL:" + sql.toString());
            long start = System.currentTimeMillis();
            pst = con.prepareStatement(sql.toString());
            rsltSet = pst.executeQuery();
            long time_executeQuery = System.currentTimeMillis();
            Object object = null;
            while (rsltSet.next()) {
                object = cls.newInstance();
                for (int i = 0; i < fieldNames.length; i++) {
                    String fieldName = fieldNames[i];
                    String firstChatacter = fieldName.substring(0, 1).toUpperCase();
                    String setMethodName = "set" + firstChatacter + fieldName.substring(1);
                    String getMethodName = "get" + firstChatacter + fieldName.substring(1);
                    Method getMethod = object.getClass().getMethod(getMethodName);
                    Class<?> cla = getMethod.getReturnType();
                    Method setMethod = object.getClass().getMethod(setMethodName, cla);
                    if (cla == String.class) {
                        setMethod.invoke(object, rsltSet.getString(columns[i]));
                    } else if (cla == long.class||cla==Long.class) {
                        setMethod.invoke(object, rsltSet.getLong(columns[i]));
                    } else if (cla == int.class) {
                        setMethod.invoke(object, rsltSet.getInt(columns[i]));
                    } else if (cla == Timestamp.class) {
                        setMethod.invoke(object, rsltSet.getTimestamp(columns[i]));
                    } else if (cla == BigDecimal.class) {
                        setMethod.invoke(object, rsltSet.getBigDecimal(columns[i]));
                    } else if (cla == Blob.class) {
                        setMethod.invoke(object, rsltSet.getBlob(columns[i]));
                    } else if (cla == Boolean.class) {
                        setMethod.invoke(object, rsltSet.getBoolean(columns[i]));
                    } else if (cla == Time.class) {
                        setMethod.invoke(object, rsltSet.getTime(columns[i]));
                    } else if (cla == Double.class||cla == double.class) {
                        setMethod.invoke(object, rsltSet.getDouble(columns[i]));
                    }else if (cla == byte[].class) {
        				BLOB blob = (BLOB) rsltSet.getBlob(columns[i]);
        				InputStream is = blob.getBinaryStream();
						setMethod.invoke(object, StreamUtil.getByteArrayByInputStream(is));
                    }
                }
                list.add(object);
            }
            long time_encapeObject = System.currentTimeMillis();
            LogUtil.debug("***********in BaseDao find result size:" + list.size()+"  WasteExecuteQuery:"+(time_executeQuery-start)+"  WasteEncapeObject:"+(time_encapeObject-time_executeQuery));
            return list;
        } catch (SQLException e) {
            LogUtil.error("error", e);
            throw e;
        } catch (Exception e) {
            LogUtil.error("error", e);
            throw new Exception(e);
        } finally {
            closeResultSet(rsltSet);
            closeStream(pst);
        }
    }


    /**
     * get value by sql,current method is use for return database sequence value
     *
     * @param con
     * @param sql
     * @param FIELD_NAME
     * @return
     * @throws SQLException
     */
    protected long find(Connection con, String sql, String FIELD_NAME) throws SQLException {
        PreparedStatement pst = null;
        ResultSet rsltSet = null;
        try {

            LogUtil.debug("find:"+FIELD_NAME+" SQL:" + sql.toString());
            pst = con.prepareStatement(sql.toString());
            rsltSet = pst.executeQuery();
            while (rsltSet.next()) {
                return rsltSet.getLong(FIELD_NAME);
            }
        } finally {
            closeResultSet(rsltSet);
            closeStream(pst);
        }
        return 0;
    }


    /**
     * close statement and the Connection
     *
     * @param statement
     * @param con
     */
    public void closeStream(Statement statement) {
        try {
            /**close Statement stream*/
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException se) {
            LogUtil.error("SQLException: Can't close PreparedStatement!", se);
        }
    }


    /**
     * close statement and the Connection
     *
     * @param statement
     * @param con
     */
    public void closeResultSet(ResultSet rs) {
        try {
            /**close Statement stream*/
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException se) {
            LogUtil.error("SQLException: Can't close rs!", se);
        }
    }


    /**
     * 1.reflect you object by fieldNames to get it's method(we may catch exception if it's boolean type)
     * 2.invoke the method to get it's value
     * 3.set it to PreparedStatement by your field's type and value
     *
     * @param preparedStatement
     * @param fieldNames
     * @param object
     * @throws SQLException
     * @throws Exception
     */
    private void invoke(PreparedStatement preparedStatement, String[] fieldNames, Object object) throws SQLException, Exception {
        try {
            for (int i = 0; i < fieldNames.length; i++) {
                String fieldName = fieldNames[i];
                String firstChatacter = fieldName.substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstChatacter + fieldName.substring(1);
                Method getMethod = null;
                try {
                    getMethod = object.getClass().getMethod(getMethodName);
                } catch (NoSuchMethodException e) {
                    getMethodName = "is" + firstChatacter + fieldName.substring(1);//catch it if it fail?
                    getMethod = object.getClass().getMethod(getMethodName);
                }
                Class<?> cla = getMethod.getReturnType();
                Object value = getMethod.invoke(object);
                /**reflect by getMethodType.If dataBase has blog or others complex type column we can override the save/update/delete method
                 * and don't use current invoke method
                 */
                if (cla == String.class) {
                    if (value != null) {
                        value = value.toString().trim();
                        if (value.toString().contains("'")) {
                            throw new Exception("invalid parameter:" + fieldNames[i] + ",it could not contains \"'\"!");
                        }
                        preparedStatement.setString(i + 1, value.toString());
                    } else {
                        preparedStatement.setString(i + 1, null);
                    }

                } else if (cla == float.class || cla == Float.class) {
                    preparedStatement.setFloat(i + 1, (Float) value);
                } else if (cla == Short.class || cla == short.class) {
                    preparedStatement.setShort(i + 1, (Short) value);
                } else if (cla == long.class || cla == Long.class) {
                    preparedStatement.setLong(i + 1, (Long) value);
                } else if (cla == int.class || cla == Integer.class) {
                    preparedStatement.setInt(i + 1, (Integer) value);
                }else if (cla == Double.class || cla == double.class) {
                    preparedStatement.setDouble(i + 1, value==null?0:(Double) value);
                }else if (cla == Timestamp.class) {
                    preparedStatement.setTimestamp(i + 1, new Timestamp(((Date) value).getTime()));
                } else if (cla == BigDecimal.class) {
                    preparedStatement.setBigDecimal(i + 1, (BigDecimal) value);
                } else if (cla == Blob.class) {
                    preparedStatement.setBlob(i + 1, (Blob) value);
                } else if (cla == Boolean.class || cla == boolean.class) {
                    preparedStatement.setBoolean(i + 1, (Boolean) value);
                } else if (cla == Time.class) {
                    preparedStatement.setTime(i + 1, (Time) value);
                }else if (cla == byte[].class){
                	preparedStatement.setBytes(i+1, (byte[]) value);
                }
            }
        } catch (SQLException e) {
            LogUtil.error("error", e);
            throw new SQLException(e.toString());
        } catch (Exception e) {
            LogUtil.error("error", e);
            throw new Exception(e);
        }
    }

}