/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgh.excel;

/**
 *   
 * @author lgh
 */
import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.HeaderFooter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {

    /**read the content from excel 
     * @param file  
     * @return
     */  
    public static String readExcel(File file) {
        StringBuffer sb = new StringBuffer();
        Workbook wb = null;
        try {
            //construct the Workbook object
            wb = Workbook.getWorkbook(file);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (wb == null) {
            return null;
        }

        //get the Sheet from Workbook
        Sheet[] sheet = wb.getSheets();

        if (sheet != null && sheet.length > 0) {
            //loop to all sheets
            for (int i = 0; i < sheet.length; i++) {
                //get the row nums from current sheet
                int rowNum = sheet[i].getRows();
                for (int j = 0; j < rowNum; j++) {
                    //get the cell case from current row
                    Cell[] cells = sheet[i].getRow(j);
                    if (cells != null && cells.length > 0) {
                        //loop to all cells
                        for (int k = 0; k < cells.length; k++) {
                            //read the cell value
                            String cellValue = cells[k].getContents();
                            sb.append(cellValue + "\t");
                        }
                    }
                    sb.append("\r\n");
                }
                sb.append("\r\n");
            }
        }
        //close and release
        wb.close();
        return sb.toString();
    }

    /**
     * add the header and the footer
     * @param dataSheet wait to add header sheet
     * @param left
     * @param center
     * @param right
     */
    public static void setHeader(WritableSheet dataSheet, String left, String center, String right) {
        HeaderFooter hf = new HeaderFooter();
        hf.getLeft().append(left);
        hf.getCentre().append(center);
        hf.getRight().append(right);
        //add header
        dataSheet.getSettings().setHeader(hf);
       //add footer
        //dataSheet.getSettings().setFooter(hf);
    }

    /**
	 * build a excel 
     * @param fileName 
     */
    public static void writeExcel(String fileName) {
        WritableWorkbook wwb = null;
        try {
            //����Ҫʹ��Workbook��Ĺ�����������һ����д��Ĺ�����(Workbook)����
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wwb != null) {
            //����һ����д��Ĺ�����
            //Workbook��createSheet������������������һ���ǹ���������ƣ��ڶ����ǹ������ڹ������е�λ��
            WritableSheet ws = wwb.createSheet("sheet1", 0);

            //���濪ʼ��ӵ�Ԫ��
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {
                    //������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��
                    Label labelC = new Label(j, i, "���ǵ�" + (i + 1) + "�У���" + (j + 1) + "��");
                    try {
                        //�����ɵĵ�Ԫ����ӵ���������
                        ws.addCell(labelC);
                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }

                }
            }

            try {
                //���ڴ���д���ļ���
                wwb.write();
                //�ر���Դ���ͷ��ڴ�
                wwb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }

    /**����ĳһ���ļ����Ƿ����ĳ���ؼ���
     * @param file  ���������ļ�
     * @param keyWord  Ҫ�����Ĺؼ���
     * @return
     */
    public static boolean searchKeyWord(File file, String keyWord) {
        boolean res = false;

        Workbook wb = null;
        try {
            //����Workbook��������������
            wb = Workbook.getWorkbook(file);
        } catch (BiffException e) {
            return res;
        } catch (IOException e) {
            return res;
        }

        if (wb == null) {
            return res;
        }

        //�����Workbook����֮�󣬾Ϳ���ͨ�����õ�Sheet��������������
        Sheet[] sheet = wb.getSheets();

        boolean breakSheet = false;

        if (sheet != null && sheet.length > 0) {
            //��ÿ�����������ѭ��
            for (int i = 0; i < sheet.length; i++) {
                if (breakSheet) {
                    break;
                }

                //�õ���ǰ�����������
                int rowNum = sheet[i].getRows();

                boolean breakRow = false;

                for (int j = 0; j < rowNum; j++) {
                    if (breakRow) {
                        break;
                    }
                    //�õ���ǰ�е����е�Ԫ��
                    Cell[] cells = sheet[i].getRow(j);
                    if (cells != null && cells.length > 0) {
                        boolean breakCell = false;
                        //��ÿ����Ԫ�����ѭ��
                        for (int k = 0; k < cells.length; k++) {
                            if (breakCell) {
                                break;
                            }
                            //��ȡ��ǰ��Ԫ���ֵ
                            String cellValue = cells[k].getContents();
                            if (cellValue == null) {
                                continue;
                            }
                            if (cellValue.contains(keyWord)) {
                                res = true;
                                breakCell = true;
                                breakRow = true;
                                breakSheet = true;
                            }
                        }
                    }
                }
            }
        }
        //���ر���Դ���ͷ��ڴ�
        wb.close();

        return res;
    }

    /**��Excel�в���ͼƬ
     * @param dataSheet  ������Ĺ�����
     * @param col ͼƬ�Ӹ��п�ʼ
     * @param row ͼƬ�Ӹ��п�ʼ
     * @param width ͼƬ��ռ������
     * @param height ͼƬ��ռ������
     * @param imgFile Ҫ�����ͼƬ�ļ�
     */
    public static void insertImg(WritableSheet dataSheet, int col, int row, int width,
            int height, File imgFile) {
        WritableImage img = new WritableImage(col, row, width, height, imgFile);
        dataSheet.addImage(img);
    }

    public static void main(String[] args) {

        try {
            //����һ��������
//            WritableWorkbook workbook = Workbook.createWorkbook(new File("D:/test1.xls"));
//            //������Ĺ�����
//            WritableSheet imgSheet = workbook.createSheet("Images", 0);
//            //Ҫ�����ͼƬ�ļ�
//            File imgFile = new File("D:/1.png");
//            //ͼƬ���뵽�ڶ��е�һ����Ԫ�񣬳����ռ������Ԫ��
//            insertImg(imgSheet, 0, 1, 6, 6, imgFile);
//            workbook.write();
//            workbook.close();

            //����һ��������
            WritableWorkbook workbook = Workbook.createWorkbook(new File("D:/test1.xls"));
            //������Ĺ�����
            for(int i =10;i>0;i--){
              WritableSheet dataSheet = workbook.createSheet("����ҳü"+i, 0);
              ExcelUtils.setHeader(dataSheet, "chb", "2007-03-06", "��1ҳ,��3ҳ");
            }
            workbook.write();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}

