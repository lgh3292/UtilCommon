/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.util.db;

/**
 * 
 * @author LGH
 */
public class DBMessage {
	
    public static final int SQLSERVER = 1;
    public static final int MYSQL = 1<<1;
    public static final int ORACLE = 1<<2;
    public static final int DB2 = 1<<3;
    public static final int SYBASE = 1<<4;
    public static final int INFORMIX = 1<<5;//informix
    public static final int POSTGRESQL = 1<<6;
    public static final int ACCESS = 1<<7;
    
    
	private String host;
	private int port;
	private String username;
	private String password;
	private String dbName;
	private int dbType;

	public DBMessage() {
	}

	public DBMessage(String host, int port,String dbName, String username, String password,int dbType) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dbName = dbName;
		this.dbType = dbType;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the dbType
	 */
	public int getDbType() {
		return dbType;
	}

	/**
	 * @param dbType the dbType to set
	 */
	public void setDbType(int dbType) {
		this.dbType = dbType;
	}

}
