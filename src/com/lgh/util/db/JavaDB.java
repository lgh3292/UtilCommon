/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.lgh.util.logging.LogUtil;

/**
 *
 * @author Administrator
 */
public class JavaDB {
    /* the default framework is embedded */

    public String framework = "embedded";
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public String protocol = "jdbc:derby:";
    private DBMessage message = null;
    private Connection con = null;

    public JavaDB(DBMessage message) {
        this.message = message;
    }

    /**
     * it maybe the first time for you to create a new db
     * the connection spcifies create=true to cause the database to be created.
     * to remove the database,remove the direcotry derbyDB and its contents.the directory
     * derbyDB will be created under the directory that the system property derby.system.home
     * point to ,or the current diryctory if derby.sytem.home is not set.
     * @throws InstantiationException
     */
    public Connection createDB() throws InstantiationException, SQLException, ClassNotFoundException, IllegalAccessException {
        Class.forName(driver).newInstance();
        LogUtil.info("Load the instance driver.");
        con = DriverManager.getConnection(protocol + message.getDbName() + ";create=true", message.getUsername(), message.getPassword());
        return con;
    }

    /**
     * get db connection
     * @return
     */
    public Connection getDBConection() throws Exception {
        Class.forName(driver).newInstance();
        LogUtil.info("Load the instance driver.");
        con = DriverManager.getConnection(protocol + message.getDbName(), message.getUsername(), message.getPassword());
        return con;
    }

    /**
     * shutdown  db 
     */
    public void shutdownDB() {
        if (framework.equals("embedded")) {
            boolean shutdown = false;
            try {
                DriverManager.getConnection("jdbc:derby:"+message.getDbName()+";shutdown=true");
            } catch (SQLException se) {
                shutdown = true;
            }

            if (!shutdown) {
            	LogUtil.info("Database did not shut down normally.");
            } else {
            	LogUtil.info("Database shut down normally.");
            }
        }
    }

    private void parseArguments(String[] args) {
        int length = args.length;

        for (int index = 0; index < length; index++) {
            if (args[index].equalsIgnoreCase("jccjdbcclient")) {
                framework = "jccjdbc";
                driver = "com.ibm.db2.jcc.DB2Driver";
                protocol = "jdbc:derby:net://localhost:1527/";
            }
            if (args[index].equalsIgnoreCase("derbyclient")) {
                framework = "derbyclient";
                driver = "org.apache.derby.jdbc.ClientDriver";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }

    public static void main(String[] args) {
        try {
            JavaDB javaDB = new JavaDB(new DBMessage("localhost",0,"use111r", "password", "javaDB",0));
//            Connection con = javaDB.createDB();
            Connection con = javaDB.getDBConection();
            con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try { // load the driver
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//            LogUtil.info("Load the embedded driver");
//            Connection conn = null;
//            Properties props = new Properties();
//            props.put("user", "user1");
//            props.put("password", "user1");
//            //create and connect the database named helloDB
//            conn = DriverManager.getConnection("jdbc:derby:helloDB;create=true", props);
//            LogUtil.info("create and connect to helloDB");
//            conn.setAutoCommit(false);
//
//            // create a table and insert two records
//            Statement s = conn.createStatement();
//            s.execute("create table hellotable(name varchar(40), score int)");
//            LogUtil.info("Created table hellotable");
//            s.execute("insert into hellotable values('Ruth Cao1', 861)");
//            s.execute("insert into hellotable values ('Flora Shi1', 921)");
//            // list the two records
//            ResultSet rs = s.executeQuery(
//                    "SELECT name, score FROM hellotable ORDER BY score");
//            LogUtil.info("namettscore@@@@@@@@@@@@@@@@@@@@@@   start");
//            while (rs.next()) {
//                StringBuilder builder = new StringBuilder(rs.getString(1));
//                builder.append("t");
//                builder.append(rs.getInt(2));
//                LogUtil.info(builder.toString());
//            }
//            LogUtil.info("namettscore@@@@@@@@@@@@@@@@@@@@@@    end");
//            // delete the table
//            s.execute("drop table hellotable");
//            LogUtil.info("Dropped table hellotable");
//
//            rs.close();
//            s.close();
//            LogUtil.info("Closed result set and statement");
//            conn.commit();
//            conn.close();
//            LogUtil.info("Committed transaction and closed connection");
//
//            try { // perform a clean shutdown
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException se) {
//                LogUtil.info("Database shut down normally");
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        LogUtil.info("SimpleApp finished");
    }
}
