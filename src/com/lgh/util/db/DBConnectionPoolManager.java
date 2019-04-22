package com.lgh.util.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lgh.util.logging.LogUtil;
/**
 * a simply PoolConectionManager
 * Parameters:
 * 'POOL_MAX_SIZE' is the POOL MAX size
 * 'LIFE_TIME' is the Connection's life cycle,if the time execcd the life cycle,the Connection will be close and remove from Pool.
 * @author liuguohu
 *
 */
public class DBConnectionPoolManager implements Runnable{
	
	private static final int POOL_MAX_SIZE = 10;
	private static final int LIFE_TIME = 10;//(second) the life cycle of every Connection.If time exceed,the connection will be close 
	private List<DBConnection> connections = Collections.synchronizedList(new ArrayList<DBConnection>());
	
	private static DBConnectionPoolManager CONNECTION_POOL = new DBConnectionPoolManager();
	private DBConnectionPoolManager(){
		new Thread(this).start();
	}
	
	/**
	 * waitting to released the connection if the Connection life cycle is over
	 */
	public void run(){
		while(true){
			synchronized (DBConnectionPoolManager.class) {
				List<DBConnection> removeList = null;
				for(DBConnection connection:connections){
					if(isTimeExeccd(connection)){
						if(removeList==null){
							removeList = new ArrayList<DBConnection>();
						}
						try {
							connection.getConnection().close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						removeList.add(connection);
					}
				}
				if(removeList!=null&&removeList.size()>0){
					connections.removeAll(removeList);
					LogUtil.info("The connection's life cycle is over,remove "+removeList.size()+",current pool size:"+connections.size());
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static DBConnectionPoolManager getInstance(){
		if(CONNECTION_POOL==null){
			CONNECTION_POOL = new DBConnectionPoolManager();
		}
		return CONNECTION_POOL;
	}
	
	/**
	 * if a connection is execcd the time 
	 * @param connection
	 * @return
	 */
	private boolean isTimeExeccd(DBConnection connection){
		if((connection.getStartTime()+LIFE_TIME*1000)<System.currentTimeMillis()){
			return true;
		}
		return false;
	}
	/**
	 * whether a Connection is closed
	 * @param dbConnection
	 * @return
	 */
	private boolean isClosed(Connection dbConnection){
		boolean close = false;
		try {
			if(dbConnection.isClosed()){
				close = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			close = true;
		}
		return close;
	}
	
	
	public synchronized Connection getConnection(){
		synchronized (DBConnectionPoolManager.class) {
			if(connections.size()>0){
				int lastIndex = connections.size()-1;
				DBConnection dbConnection = connections.get(lastIndex);
				connections.remove(dbConnection);
				if(isClosed(dbConnection)){//if the connection is closed,we will remove this connection and return a new connection to user.
					LogUtil.info("The connection getting from pool is Closed,will new a Conection to user directly!,rest Pool size:"+connections.size());
					Connection con = DBConnectionManager.getLocalOracleInstance().getConnection();
					return new DBConnection(this,con);
				}else{
					LogUtil.info("Get a connection from pool and it is ok,rest Pool size:"+connections.size());
					return dbConnection;
				}
			}else{
				LogUtil.info("Current Pool's size <=0,will new a Conection to user,current Pool size:"+connections.size());
				Connection con = DBConnectionManager.getLocalOracleInstance().getConnection();
				return new DBConnection(this,con);
			}
		}
	}


	/**
	 * close a connection,if this connection is closed,will not the add to Connection Pool.
	 * @param dbConnection
	 * @throws SQLException
	 */
	public synchronized void closeConnection(DBConnection dbConnection) throws SQLException {
		synchronized (DBConnectionPoolManager.class) {
			if(connections.size()<POOL_MAX_SIZE){
				if(isClosed(dbConnection)){
					LogUtil.info("Db is close,this Connection will not be add to Pool,current Pool size:"+connections.size());
				}else{
					connections.add(dbConnection);
					LogUtil.info("add a Connection to Pool,current Pool size:"+connections.size());
				}
			}else{
				dbConnection.getConnection().close();
				LogUtil.info("Pool's size execcd POOL_MAX_SIZE,the connection will not be added to pool,current Pool size:"+connections.size());
			}
		}
	}

	
	
	public static void main(String[] args) throws Exception{
		Connection con1 = DBConnectionPoolManager.getInstance().getConnection();
		Connection con2 = DBConnectionPoolManager.getInstance().getConnection();
		Connection con3 = DBConnectionPoolManager.getInstance().getConnection();
		Connection con4 = DBConnectionPoolManager.getInstance().getConnection();
		con1.close();
		con2.close();
		con3.close();
		con4.close();
		
		for(int i=0;i<100;i++){
			Connection con5 = null;
			try {
				Thread.sleep(3000);
				 con5 = DBConnectionPoolManager.getInstance().getConnection();
//				 Statement statement = (Statement) con5.createStatement();
//		            ResultSet rs = ((java.sql.Statement) statement).executeQuery("select * from test");
//		            while(rs.next()){
//		                LogUtil.info(rs.getObject(1));
//		            }
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				try {
					con5.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
