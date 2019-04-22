package com.lgh.util.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection extends DBDecoratorConnection{
	private final long startTime;
	private DBConnectionPoolManager connectionPool;
	public DBConnection(DBConnectionPoolManager connectionPool,Connection connection) {
		super(connection);
		this.connectionPool = connectionPool;
		startTime = System.currentTimeMillis();
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	
	/* (non-Javadoc)
	 * @see com.lgh.util.db.DecoratorConnection#close()
	 */
	@Override
	public void close() throws SQLException {
		connectionPool.closeConnection(this);
	}
	
	

	
}
