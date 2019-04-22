package com.lgh.util.db;


import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lgh
 */
public class MainTest {

    public static void main(String[] args) {
        try {
//            String test = java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("hello");
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@sqlserver connect the first Connection type ( of the DBConnecion)
//            DBConnection dbc = new DBConnection(java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("192.168.1.56"), 1433, java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("llk"), java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("sa"), java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("123456"), DBConnection.SQLSERVER);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@connect to mysql
//            DBConnection dbc = new DBConnection("127.0.0.1", 3306, "mysql", "root", "tewang",DBConnection.MYSQL);
//            ResultSet rs = dbc.executeQuery("select count(*) from user");

            //sqlserver
            
//            DBConnection dbc = new DBConnection("168.5.12.44", 1433, "MIS", "cibecs", "ecscib",DBConnection.SQLSERVER);
//            boolean rs = dbc.execute("drop table auth_temp");
//            LogUtil.info(rs);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@连接informix数据库 ???????????fail
//            DBConnection informixConnection = new DBConnection("192.168.110.18", 3333, "sdcf:INFORMIXSERVER=demo_on", "root", "lghlgh",DBConnection.SYSPROPS);
//
//            ResultSet rs1 = informixConnection.executeQuery("select count(*) from user");
//            IfxDriver dr = new IfxDriver();
//			DriverManager.registerDriver(dr);
//			Class.forName("com.informix.jdbc.IfxDriver").newInstance();
//	        String url = "jdbc:informix-sqli://98.237.21.62:1059/ibmserver:INFORMIXSERVER=bbsbbs;" +
//	        		"user=root;password=lghlgh";
//	        Connection conn= (Connection) DriverManager.getConnection(url);
//	        LogUtil.info(conn);

//            try {
//                IfxDriver dr = new IfxDriver();
//                DriverManager.registerDriver(dr);
//                Class.forName("com.informix.jdbc.IfxDriver").newInstance();
//                String url = "jdbc:informix-sqli://192.168.100.16:15001/lzp:INFORMIXSERVER=demo_on1;" +
//                        //"user=informix;password=informix;DB_LOCALE=en_US.819;CLIENT_LOCALE=en_US.819;DBDATE=MDY4";
//                        "user=informix;password=informix;DB_LOCALE=en_US.819";
//                Connection conn = DriverManager.getConnection(url);
//                LogUtil.info(conn);

//            } catch (Exception ex){
//                ex.printStackTrace();
//            }

            //the second connection type(of the DBConnection2)
            //            long time = System.currentTimeMillis();
            //            Connection con = DBConnection2.getConnection(java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("localhost"), 1433, java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("llk"), java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("sa"), java.util.ResourceBundle.getBundle("com/haohong/dbconnect/test/test").getString("123456"), DBConnection.SQLSERVER);
            //****************statement
            //            con.setAutoCommit(false);
            //            Statement stmt = con.createStatement();
            //            stmt.addBatch("insert into llk(id) values(5)");
            //            stmt.addBatch("insert into llk(id) values(6)");
            //            stmt.addBatch("insert into llk(id) values(7)");
            //            stmt.addBatch("insert into llk(id) values(8)");
            //            int[] num = stmt.executeBatch();
            //            con.commit();
            //**************CallableStatement
            //            CallableStatement cs = con.prepareCall("CALL p_test(?,?,?)");
            //            cs.setInt(1, 11111);
            //            cs.setInt(2, 33333);
            //            cs.addBatch();
            //            cs.executeUpdate();
            ////            DatabaseMetaData meta = con.getMetaData();
            ////            ResultSet rs = meta.getTables(null, null, "EmpTable", null);
            //
            ////            DatabaseMetaData meta = con.getMetaData();
            ////            String[] tableTypes = {"TABLE"};
            ////            ResultSet rs = meta.getTables("empCatalog", "empSchema", "EmpTable", tableTypes);
            //
            //            DatabaseMetaData data = con.getMetaData();//show the db names
            //            ResultSet rs = data.getCatalogs();
            //            while (rs.next()) {
            //                LogUtil.info(rs.getString(1));
            //            }
            //            rs.close();
            //            con.close();
            //            LogUtil.info((System.currentTimeMillis() - time));
            /**
             * setAutoCommit
             */
            //            con.setAutoCommit(false);
            //            Statement stmt = con.createStatement();
            //            stmt.executeUpdate("insert into llk(id) values(13)");
            //            stmt.executeUpdate("insert into llk(id) values(16)");
            //            con.rollback();
            //            stmt.executeUpdate("insert into llk(id) values(17)");
            //            con.commit();
            //********执行结果是就插入了一条 id = 17的值,前面两条没有执行因为调用了rollback();


            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@connect to oracle
        	DBMessage message = new DBMessage("localhost",1521,"oracle","test","test",DBMessage.ORACLE);
            Connection con = DBConnectionManager.getInstance(message).getConnection();
            con.setAutoCommit(false);
        	Statement statement = con.createStatement();
            statement.execute("insert into test(id) values(3)");
            con.commit();
            con.rollback();
//            LogUtil.info(i);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@connect to db2
//             DBConnection db2Connect = new DBConnection("192.168.110.18",50000,"sample","db2inst1","lghlgh",DBConnection.DB2);
//             Statement statement = db2Connect.getStmt();
//            ResultSet rs = statement.executeQuery("select * from sales");
//            while(rs.next()){
//                LogUtil.info(rs.getString(1));
//            }
        } catch (Exception ex) {
        	ex.printStackTrace();
                Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
