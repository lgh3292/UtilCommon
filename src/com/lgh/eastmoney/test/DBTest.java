package com.lgh.eastmoney.test;

import org.apache.log4j.Logger;

public class DBTest {
//	private static Log log = LogFactory.getLog(DBTest.class);
	static Logger log = Logger.getLogger(DBTest.class); 
//	static Logger log = Logger.getLogger("LOG1"); 
	public void testInsert(){
		for(int i=0;i<10000000;i++)
		log.error("1test");
//		DBMessage message = new DBMessage("localhost", 1521, "oracle", "test","test", DBMessage.ORACLE);
//		final Connection con = DBConnectionManager.getInstance(message).getConnection();
//		try {
//			con.setAutoCommit(false);
//			PreparedStatement statement = con.prepareStatement("insert into test(id,text) values(?,?)");
//			statement.setInt(1, 11);
//			statement.setString(2,null);
//			statement.execute();
//			con.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

	
				
	}
	public static void main(String[] args) {
		new DBTest().testInsert();
//		DBMessage message = new DBMessage("localhost", 1521, "oracle", "test","test", DBMessage.ORACLE);
//		final Connection con = DBConnectionManager.getInstance(message).getConnection();
//		try {
//			con.setAutoCommit(false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		new Thread(new Runnable(){
//			public void run(){
//				try {
//					PreparedStatement statement = con.prepareStatement("insert into test(id,text) values(?,?)");
//					
//					Thread.sleep(1000);
//					LogUtil.info("sleep 1");
//					statement.execute("insert into test(id,text) values(9,'')");
//					LogUtil.info("execute 1");
//					
//					con.commit();
//					LogUtil.info("commit 1");
//					con.rollback();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//		
//		new Thread(new Runnable(){
//			public void run(){
//				try {
//					Statement statement = con.createStatement();
//					Thread.sleep(1000);
//					LogUtil.info("sleep 2");
//					statement.execute("insert into test(id) values(7)");
//					LogUtil.info("execute 2");
//					con.commit();
//					LogUtil.info("commit 2");
//					con.rollback();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();

	}
}
