package com.linuxense.javadbf;

import java.io.FileOutputStream;
import java.io.IOException;

public class DBFWriterTest {

  public static void main( String args[])
  throws DBFException, IOException {

    // let us create field definitions first
    // we will go for 3 fields
    //
    DBFField fields[] = new DBFField[ 3];

    fields[0] = new DBFField();
    fields[0].setName( "emp_code");
    fields[0].setDataType( DBFField.FIELD_TYPE_C);
    fields[0].setFieldLength( 10);

    fields[1] = new DBFField();
//    fields[1].setField( "emp_name");
    fields[1].setDataType( DBFField.FIELD_TYPE_C);
    fields[1].setFieldLength( 20);

    fields[2] = new DBFField();
//    fields[2].setField( "salary");
    fields[2].setDataType( DBFField.FIELD_TYPE_N);
    fields[2].setFieldLength( 12);
    fields[2].setDecimalCount( 2);

    DBFWriter writer = new DBFWriter();
    writer.setFields( fields);

    // now populate DBFWriter
    //

    Object rowData[] = new Object[3];
    rowData[0] = "1000";
    rowData[1] = "John";
    rowData[2] = new Double( 5000.00);

    writer.addRecord( rowData);

    rowData = new Object[3];
    rowData[0] = "1001";
    rowData[1] = "Lalit";
    rowData[2] = new Double( 3400.00);

    writer.addRecord( rowData);

    rowData = new Object[3];
    rowData[0] = "1002";
    rowData[1] = "Rohit";
    rowData[2] = new Double( 7350.00);

    writer.addRecord( rowData);

    FileOutputStream fos = new FileOutputStream( args[0]);
    writer.write( fos);
    fos.close();
  }
}
