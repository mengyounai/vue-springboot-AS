package com.example.wxbf.aliyun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileTest {
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    File file = new File("C:\\Users\\OvO\\Desktop\\q\\数据.txt");


    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public String[] qiege(){
        int l=0;
        int n=0;
        FileTest file=new FileTest();
        String text=txt2String(file.file);
        String[] strarray=text.split(",");
        boolean a=false;
        boolean b=false;

//        for (int i = 0; i < strarray.length; i++) {
//            System.out.println(strarray[i]);
//        }

        //获取有多少数据
        for (int i = 0; i < strarray.length; i++) {
            if (strarray[i].contains("Identifier")){
                n++;
            }
        }
        System.out.println(n);
        String[] strings=new String[n];

        for (int i = 0; i < strarray.length; i++) {
            if (strarray[i].contains("Identifier")){
                a=true;
            }
            if (strarray[i].contains("Value")){
                a=true;
            }
            if (strarray[i].contains("Time")){
                b=true;
            }
            if (a){
                int index=strarray[i].lastIndexOf(":");
                strings[l]+=strarray[i].substring(index+1,strarray[i].length());
                a=false;
            }
            if (b){
                strings[l]+=""+strarray[i];
                b=false;
                l++;
            }
        }
        String[] result=new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i]=strings[i].replace("null", "");
//            System.out.println(result);
        }


        return result;
    }


    public static void main(String[] args){
        FileTest file=new FileTest();
//        System.out.println(txt2String(file.file));
        String[] text=file.qiege();
        for (String s : text) {
            System.out.println(s);
        }

    }
}