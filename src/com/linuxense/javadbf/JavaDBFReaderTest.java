package com.linuxense.javadbf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.lgh.util.logging.LogUtil;

public class JavaDBFReaderTest {

  public static void main( String args[]) {

    try {

      // create a DBFReader object
      //
      InputStream inputStream  = new FileInputStream("c://GAME_DATA.DBF"); // take dbf file as program argument
      DBFReader reader = new DBFReader( inputStream); 

      // get the field count if you want for some reasons like the following
      //
      int numberOfFields = reader.getFieldCount();

      // use this count to fetch all field information
      // if required
      //
      for( int i=0; i<numberOfFields; i++) {

        DBFField field = reader.getField( i);

        // do something with it if you want
        // refer the JavaDoc API reference for more details
        //
        LogUtil.info( field.getName());
      }

      // Now, lets us start reading the rows
      //
      Object []rowObjects;

      while( (rowObjects = reader.nextRecord()) != null) {

        for( int i=0; i<rowObjects.length; i++) {

        	LogUtil.info( rowObjects[i]);
        }
      }

      // By now, we have itereated through all of the rows
      
      inputStream.close();
    }
    catch( DBFException e) {

    	LogUtil.info( e.getMessage());
    }
    catch( IOException e) {

    	LogUtil.info( e.getMessage());
    }
  }  
}  
