package com.lgh.util.db;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.lgh.util.StreamUtil;
import com.lgh.util.logging.LogUtil;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;
import testpackage.Customer;
/**
 * import/export blob from database's various method with shell implement,or
 * java implement,or oracle procedure implement
 * @author liuguohua
 *
 */
public class ImportExportBlobTest {
	private DBMessage message;
	public ImportExportBlobTest(DBMessage message){
		this.message = message;
	}
	/**
	 * create table test( id int primary key, content blob );
	 * @throws SQLException 
	 */
	public void createTable() throws SQLException{
        String sql = "create table test( id int primary key, content blob ,CONTENT_TEXT CLOB )";
        String tableName = "test";
        DBConnectionManager.getInstance(message).createTable(tableName, sql);
	}
	
	/************************************************************************
	 * for insert blob to db there has various methods
	************************************************************************/
	public void insertBLOB1_OracleProcedure(){
		/** insert 1 by oracle procedure
		  a.CREATE OR REPLACE DIRECTORY images AS 'C:\';
		  b.We import the file into a BLOB datatype and insert it into the table
			DECLARE
			  l_bfile  BFILE;
			  l_blob   BLOB;
			BEGIN
			  INSERT INTO test (id, content)
			  VALUES (1, empty_blob())
			  RETURN content INTO l_blob;
			
			  l_bfile := BFILENAME('IMAGES', 'MyImage.gif');
			  DBMS_LOB.fileopen(l_bfile, Dbms_Lob.File_Readonly);
			  DBMS_LOB.loadfromfile(l_blob, l_bfile, DBMS_LOB.getlength(l_bfile));
			  DBMS_LOB.fileclose(l_bfile);
			
			  COMMIT;
			END;
			/
		
		  c.To update an existing BLOB do the following.
			DECLARE
			  l_bfile  BFILE;
			  l_blob   BLOB;
			BEGIN
			  SELECT content
			  INTO   l_clob
			  FROM   test
			  WHERE  id = 4
			  FOR UPDATE;
			
			  l_bfile := BFILENAME('IMAGES', 'MyImage.gif');
			  DBMS_LOB.fileopen(l_bfile, Dbms_Lob.File_Readonly);
			  DBMS_LOB.loadfromfile(l_blob, l_bfile, DBMS_LOB.getlength(l_bfile));
			  DBMS_LOB.fileclose(l_bfile);
			END;
			/	
		 */
				
	}

	/**
	 * insert 3
	 */
	public void insertBLOB3(){
		Connection con = null;
		try {
			con = DBConnectionManager.getInstance(message).getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			st.executeUpdate("insert into test values(5,empty_blob(),empty_clob())");
			ResultSet rs = st.executeQuery("select content from test where id = 5 for update");
			if(rs.next()){
				oracle.sql.BLOB blob = (BLOB) rs.getBlob(1);
				OutputStream os = blob.getBinaryOutputStream();
				File file = new File("c://test3.txt");
				InputStream is = new FileInputStream(file);
				byte[] b = new byte[blob.getBufferSize()];
				int len = 0;
				while((len=is.read(b))!=-1){
					os.write(b, 0, len);
				}
				is.close();
				os.flush();
				os.close();
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con == null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * insert 4
	 */
	public void insertBLOB4(){
		Connection con = null;
		try {
			con = DBConnectionManager.getInstance(message).getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			st.executeUpdate("insert into test values(3,empty_blob())");
			ResultSet rs = st.executeQuery("select content from test where id = 3 for update");
			if(rs.next()){
				java.sql.Blob blob = ((	OracleResultSet)rs).getBLOB(1);
				File file = new File("c://ES1010_g3u802_es3.csv");
				InputStream is = new FileInputStream(file);
				int chunk = 32000;
				byte[] b = new byte[chunk];
				OracleCallableStatement cstmt = (OracleCallableStatement)con.prepareCall("" +
						"begin dbms_lob.writeappend( :1, :2, :3);end;");
				cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.BLOB);
				int read = 0;
				while((read = is.read(b))!=-1){
					cstmt.setBlob(1, blob);
					cstmt.setInt(2, read);
					cstmt.setBytes(3, b);
					cstmt.executeUpdate();
					blob = cstmt.getBlob(1);
				}
				is.close();
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con == null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * insert 5
	 */
	public void insertBLOB_TEXT5(Object object){
        byte[] data =  StreamUtil.getByteArrayByObject(object);
		PreparedStatement ps=null;
        String update="insert into test (id,content) values(?,?)";
        Connection con = DBConnectionManager.getInstance(message).getConnection();
        try {
            ps=con.prepareStatement(update);
            ps.setInt(1,5);
            LogUtil.info(new String(data));
//            ps.setBytes(2,data);
            ps.setBinaryStream(2, new ByteArrayInputStream(data),data.length);
            int i=ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(ps!=null){
                    ps.close();
                }
                if(con!=null){
                	con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		
	}
	
	
	/************************************************************************
	 * for export BLOB content from database,there also has various method
	 * 
	 ************************************************************************/
	//way1 export BLOB content from database
	public void exportBLOB1_JavaProcedure(){
		/**
		   1.First we create a Java stored procedure that accepts a file name and a BLOB as parameters
		   
		   
		   
		   	CREATE OR REPLACE JAVA SOURCE NAMED "BlobHandler" AS
				import java.lang.*;
				import java.sql.*;
				import oracle.sql.*;
				import java.io.*;
				
				public class BlobHandler
				{
				  
				  public static void ExportBlob(String myFile, BLOB myBlob) throws Exception
				  {
				    // Bind the image object to the database object
				    // Open streams for the output file and the blob
				    File binaryFile = new File(myFile);
				    FileOutputStream outStream = new FileOutputStream(binaryFile);
				    InputStream inStream = myBlob.getBinaryStream();
				
				    // Get the optimum buffer size and use this to create the read/write buffer
				    int size = myBlob.getBufferSize();
				    byte[] buffer = new byte[size];
				    int length = -1;
				
				    // Transfer the data
				    while ((length = inStream.read(buffer)) != -1)
				    {
				      outStream.write(buffer, 0, length);
				      outStream.flush();
				    }
				
				    // Close everything down
				    inStream.close();
				    outStream.close();
				  } 
				
				};
				/


			ALTER java source "BlobHandler" compile;
			show errors java source "BlobHandler"
			
			2.Next we publish the Java call specification so we can access it via PL/SQL:
				CREATE OR REPLACE PROCEDURE ExportBlob (p_file  IN  VARCHAR2,
				                                        p_blob  IN  BLOB)
				AS LANGUAGE JAVA 
				NAME 'BlobHandler.ExportBlob(java.lang.String, oracle.sql.BLOB)';
				/
		 
		 	3.Next we grant the Oracle JVM the relevant filesystem permissions(by sysdba):
				EXEC DBMS_JAVA.grant_permission('SCHEMA-NAME', 'java.io.FilePermission', '<<ALL FILES>>', 'read ,write, execute, delete');
				EXEC Dbms_Java.Grant_Permission('SCHEMA-NAME', 'SYS:java.lang.RuntimePermission', 'writeFileDescriptor', '');
				EXEC Dbms_Java.Grant_Permission('SCHEMA-NAME', 'SYS:java.lang.RuntimePermission', 'readFileDescriptor', '');
				
				SCHEMA-NAME should be take place with my schema name example:
					EXEC DBMS_JAVA.grant_permission('TEST', 'java.io.FilePermission', 'c://test', 'read ,write, execute, delete');
					EXEC Dbms_Java.Grant_Permission('TEST', 'SYS:java.lang.RuntimePermission', 'writeFileDescriptor', '');
					EXEC Dbms_Java.Grant_Permission('TEST', 'SYS:java.lang.RuntimePermission', 'readFileDescriptor', '');
		
			4.finally,we can test it by follow codes:
				  DECLARE
				  l_blob BLOB;
					BEGIN
					  SELECT content
					  INTO   l_blob
					  FROM   test where id =14;
					  
					  ExportBlob('c:\test\test.bmp',l_blob);
					END;
					/
				
		 *
		 */
	}
	
	
	//export BLOB content using UTL_FILE
	public void exportBLOB2_UTL_FILE(){
		 /**
		1.First we create a directory object pointing to the destination directory(with sysdba)
			# connect / as sysdba
			# create or replace directory BLOBDIR as 'c://test';
			#grant read,write on directory BLOBDIR to eygle; 

		2.Next we open the BLOB, read chunks into a buffer and write them to a file:
			CREATE OR REPLACE DIRECTORY BLOBS AS 'c:\test';
			DECLARE
			  l_file      UTL_FILE.FILE_TYPE;
			  l_buffer    RAW(32767);
			  l_amount    BINARY_INTEGER := 32767;
			  l_pos       INTEGER := 1;
			  l_blob      BLOB;
			  l_blob_len  INTEGER;
			BEGIN
			  -- get blob from db
			  select content into l_blob from test where id =3;
			  
			  l_blob_len := DBMS_LOB.getlength(l_blob);
			  dbms_output.put_line('the blob length:'||l_blob_len);
			
			  -- Open the destination file.
			  l_file := UTL_FILE.fopen('BLOBS','test2.csv','wb', 32767);
			
			  -- Read chunks of the BLOB and write them to the file
			  -- until complete.
			  WHILE l_pos < l_blob_len LOOP
			    DBMS_LOB.read(l_blob, l_amount, l_pos, l_buffer);
			    UTL_FILE.put_raw(l_file, l_buffer, TRUE);
			    l_pos := l_pos + l_amount;
			  END LOOP;
			  dbms_output.put_line('the l_pos:'||l_pos);
			  -- Close the file.
			  UTL_FILE.fclose(l_file);
			  
			EXCEPTION
			  WHEN OTHERS THEN
			    -- close file if the file is still open
			    IF UTL_FILE.is_open(l_file) THEN
			      UTL_FILE.fclose(l_file);
			    END IF;
			    RAISE;
			END;
			/

	  */
	}
	
	public void exportBLOB_Spool(){
		/**
			2.spool the blob
				set termout off echo off trimspool on recsep off feedback off linesize 1000 pages 0;
				column dttm new_value v_dttm noprint;
				select to_char(sysdate, 'yyyymmddhh24miss') dttm from dual;
				spool c://test//batchCsvUnSend.&v_dttm..txt replace;
				select Utl_Raw.Cast_To_Varchar2(content) from test where id =3;                           
				spool off;
		 */
	}
	/**
	 * read blob from database way3
	 */
	public void exportBLOB3(){
		Connection con = null;
		try {
			con = DBConnectionManager.getInstance(message).getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select content from test where id = 5");
			if(rs.next()){
				BLOB blob = (BLOB) rs.getBlob(1);
				InputStream is = blob.getBinaryStream();
				File file = new File("c://test3.txt");
				OutputStream os = new FileOutputStream(file);
				byte[] b = new byte[blob.getBufferSize()];
				int len = 0;
				while((len=is.read(b))!=-1){
					os.write(b, 0, len);
				}
				is.close();
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con == null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * read blob from database way4
	 */
	public void exportBLOB_TEXT4(){
		Connection con = null;
		try {
			con = DBConnectionManager.getInstance(message).getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select content from test where id = 5");
			if(rs.next()){
				BLOB blob = (BLOB) rs.getBlob(1);
				InputStream is = blob.getBinaryStream();
				byte[] bytes = StreamUtil.getByteArrayByInputStream(is);
				LogUtil.info(new String(bytes));
//				LogUtil.info(StreamUtil.readFromStream(bytes));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(con == null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			Customer cust = new Customer("272401534", new Date(), new ArrayList());
			DBMessage message = new DBMessage("localhost",1521,"oracle","test","test",DBMessage.ORACLE);
			ImportExportBlobTest b = new ImportExportBlobTest(message);
//			b.createTable();
			b.insertBLOB3();
//			b.exportBLOB3();
//			b.insertBLOB_TEXT5("insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)insertcom.lgh.eastmoney.EastMoneyVolume sql Stringinsert into east_money_volume(EM_VOLUME_ID,EM_STOCK_ID,EM_VOLUME_DETAIL,EM_VOLUME_DATE) values(east_money_volume_sequence.nextval,?,?,?)");
			b.exportBLOB_TEXT4();
			
//			DBConnectionManager.getInstance(message).testDeclear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
