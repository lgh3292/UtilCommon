/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lgh
 */
public class DBConnectionManager {

	private static DBConnectionManager instance;;
	
    private Statement statement;
    private DBMessage message;
    
    private DBConnectionManager() {

    }
    /**
     * connect to SQL
     * @param host
     * @param port
     * @param dbName
     * @param userName
     * @param password
     */
    private DBConnectionManager(DBMessage message) {
        this.message = message;
    }
    
    public static DBConnectionManager getLocalOracleInstance(){
    	DBMessage message = new DBMessage("lgh3292.gicp.net",44966,"DNAF","TEST","TEST",DBMessage.ORACLE);
    	return getInstance(message);
    }
    
    public static DBConnectionManager getInstance(DBMessage message){
    	if(instance ==null){
    		synchronized (DBConnectionManager.class) {
				if(instance ==null){
					instance = new DBConnectionManager(message);
				}
			}
    	}
    	return instance;
    }
    
    public Connection getConnection(){
    	Connection connection = null;
    	try {
        	String host = message.getHost();
        	int port = message.getPort();
        	String dbName = message.getDbName();
        	String username = message.getUsername();
        	String password = message.getPassword();
            switch (message.getDbType()) {
                case DBMessage.SQLSERVER:
                    String url = "jdbc:sqlserver://" + host + ":" + port + ";" + "dataBaseName=" + dbName;
//                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();//method1(the way of load Driver
//                    DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());//method2
//                    com.microsoft.jdbc.sqlserver.SQLServerDriver sql = new com.microsoft.jdbc.sqlserver.SQLServerDriver();//method3
                    connection = DriverManager.getConnection(url, username, password);
                    break;
                case DBMessage.MYSQL:
                    String url1 = "jdbc:mysql://" + host + "/" + dbName + "?characterEncoding=utf-8&user=";
//                    String url1 = "jdbc:mysql://" + host + "?&useUnicode=true&characterEncoding=8859_1";
                    //following way is the first way to load driver
//                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    //the dirver is also could be the following
                    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                    connection = DriverManager.getConnection(url1, username, password);
                    break;
                case DBMessage.ORACLE://ojdbc7.jar use for oracle 12C , ojdbc14.jar for the old version
                    String url2 = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
                    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                    connection = DriverManager.getConnection(url2, username, password);
                    break;
                case DBMessage.DB2:
                    Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
                    String url4 = "jdbc:db2://" + host + ":" + port + "/" + dbName; //sample为你的数据库名 
                    connection = DriverManager.getConnection(url4, username, password);
                    break;
                case DBMessage.SYBASE:
                    Class.forName("com.sybase.jdbc.SybDriver").newInstance();
                    String url5 = " jdbc:sybase:Tds:" + host + ":" + port + "/" + dbName;//myDB为你的数据库名 
                    Properties sysProps = System.getProperties();
                    sysProps.put("user", "userid");
                    sysProps.put("password", "user_password");
                    connection = DriverManager.getConnection(url5, sysProps);
                    break;
                case DBMessage.INFORMIX:
                    Class.forName("com.informix.jdbc.IfxDriver").newInstance();
                    String url6 = "jdbc:informix-sqli://" + host + ":" + port + "/" + dbName;
                    connection = DriverManager.getConnection(url6, username, password);
                    break;
                case DBMessage.POSTGRESQL:
                    Class.forName("org.postgresql.Driver").newInstance();
                    String url7 = "jdbc:postgresql://" + host + "/" + dbName; 
                    connection = DriverManager.getConnection(url7, username, password);
                    break;
                case DBMessage.ACCESS:
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    String url8 = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=" + dbName;
                    connection = DriverManager.getConnection(url8, username, password);
                    break;
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }    		
        return connection;
    }

    /**
     * return all the db names
     * @throws SQLException 
     */
    public ResultSet getCatalogs() throws SQLException {
        return getConnection().getMetaData().getCatalogs();
    }


    public Statement getStatement() throws SQLException{
        //the first way to create Statement
    	statement = getConnection().createStatement();
        //the second way to create Statement
//        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement;
    }
 
    public void PreparedStatementTest() {
        try {
//            INSERT INTO NewUser ( userName, when ) VALUES ( ?, ? )
            PreparedStatement stmt1;
//            stmt1 = con.prepareStatement("UPDATE llk set tie = ? where id = ?");//  ok
//            stmt1 = con.prepareStatement("insert into llk values (?,?,?,?,?,?)");//ok
            stmt1 = getConnection().prepareStatement("insert into llk (id) values (?)");//ok
            stmt1.setInt(1, 11113);
            stmt1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testDeclear() throws SQLException{
    	String query = "declare "
    	+" x varchar2(10);"
    	+" begin"
    	+" x:='this is ..';"
    	+" DBMS_OUTPUT.PUT_LINE('X VALUE IS :'||x);"
    	+" END;";
    	getConnection().createStatement().execute(query);
    }
    
    /**
     * 创建表
     * @param tableName
     * @throws SQLException
     */
    public void createTable(String tableName,String sql) throws SQLException{
    	String createSQL = "declare v_cnt Number; "
    	+" begin" 
    	+" select count(*) into v_cnt from user_tables where table_name = 'TEST';" 
    	+" if v_cnt>0 then" 
    	+" dbms_output.put_line('该表存在！');" 
    	+" execute immediate 'drop table "+tableName+"';"
    	+" else" 
    	+" dbms_output.put_line('该表不存在或当前用户无权访问！');"   
    	+" end If;" 
    	+" execute immediate '"+sql+"';" 
    	+" End;";
    	getConnection().createStatement().execute(createSQL);
    }
    
    
    public static void main(String[] args) {
    	
    	try {
    		 String url2 = "jdbc:oracle:thin:@lgh3292.gicp.net:44966:DNAF";
//    		String url2 = "jdbc:oracle:thin:@118.25.53.189:1539:DNAF";
             Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
             Connection connection = DriverManager.getConnection(url2, "TEST", "TEST");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
