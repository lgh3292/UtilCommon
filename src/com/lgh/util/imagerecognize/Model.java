package com.lgh.util.imagerecognize;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lgh.util.StreamUtil;

public class Model {   
    private static byte[][] figureModel = {   
        {0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,1,1,0,0,0},   
        {0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,1,1,1,1,1,0},   
        {0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1},   
        {0,0,1,1,1,1,1,0,0,0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,1,1,0,0,0,1,1,1,1,1,0,0},   
        {0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,0,1,1,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0},   
        {0,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0},   
        {0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,1,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,1,1,0,0,0,1,1,1,1,1,0,0},   
        {0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},   
        {0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,1,0,0,0,1,1,1,1,0,0},   
        {0,0,0,1,1,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,1,0,0,0,1,1,1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,1,1,0,0}   
    };   
    private Model(){   
    }   
   
    public static byte compare(byte[] source){   
        byte result = 0;       
        byte[][] matchResult = new byte[10][6];   
        byte totalPoint = 0;   
        byte totalMatch = 0;   
        //逐个比较模式   
        for(int i=0; i <figureModel.length; i++){   
            totalPoint = 0;   
            totalMatch = 0;   
            for(int k=0; k < figureModel[i].length;  k++ ){   
                if(figureModel[i][k] == 0 ) continue;   
                totalPoint++;   
                if(source[k] == 1 ) totalMatch++;   
            }   
            matchResult[i][0] = totalPoint;   
            matchResult[i][1] = totalMatch;   
        }   
        byte maxResult = 0;   
        for(int i=0; i <matchResult.length; i++ ){   
            matchResult[i][2] = (byte)(((float)matchResult[i][1])/matchResult[i][0]*100);   
            if(matchResult[i][2]> maxResult){   
                maxResult = matchResult[i][2];   
                result = (byte)i;   
            }   
            //System.out.println(i + "-->" + matchResult[i][1]+"/" + matchResult[i][0] + "=" + matchResult[i][2] + "%");   
        }   
        return result;   
    }   
   
    public static void main(String[] args) {
		File file = new File("c://getimage.jpeg");
		try {
			byte[] bytes = StreamUtil.getByteArrayByInputStream(new FileInputStream(file));
			System.out.println(match(bytes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static String match(byte[] imageByte ){   
        String result = "";        
        int[] splitData = new int[360];   
        BufferedImage source = null;   
        try {   
            source = ImageIO.read(new ByteArrayInputStream(imageByte));   
        }   
        catch (IOException e) {   
            e.printStackTrace();   
        }   
        if( source == null ) return result;   
        source.getRGB(5, 3, 36, 10, splitData, 0, 36);   
        int[][] splitChar = new int[4][90];   
        int line = 0;   
        for(int i=0; i<splitData.length; i+=36 ){   
            System.arraycopy(splitData, i, splitChar[0], line*9, 9);   
            System.arraycopy(splitData, i+9, splitChar[1], line*9, 9);   
            System.arraycopy(splitData, i+18, splitChar[2], line*9, 9);   
            System.arraycopy(splitData, i+27, splitChar[3], line*9, 9);   
            line++;   
        }   
        byte[][] cmpData = new byte[4][90];   
        for(int i=0; i<splitChar.length; i++ ){   
            for(int k=0;k<splitChar[i].length; k++ )   
            if(splitChar[i][k] == -16777216){   
                cmpData[i][k] = 1;   
            }   
        }   
        result =  Model.compare(cmpData[0])+""+   
                  Model.compare(cmpData[1])+   
                  Model.compare(cmpData[2])+""+   
                  Model.compare(cmpData[3]);   
        return result;   
    }   
   
}   