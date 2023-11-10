package com.test.mytest.csvAnalyze;

import com.alibaba.excel.EasyExcel;
import com.opencsv.CSVWriter;
import com.test.mytest.entity.Dict;
import com.test.mytest.listener.DictDataListener;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析并生成csv文件
 */
public class CsvAnalyzer {

    //开始操作
    public static void createCsv() throws IOException {

        FileReader fr=new FileReader("E:\\microserve\\oj-backend-microservice-master (2)\\my-test\\src\\main\\java\\com\\test\\mytest\\file\\sample.csv");
        BufferedReader br=new BufferedReader(fr);
        String line="";
        //结果集
        int count = 0;
        List<String[]> data = new ArrayList<String[]>();
        while ((line=br.readLine())!=null) {
            if (count != 0) {
                String[] strings = jsonAnalyze(line);
                data.add(strings);
            }

            count++;
        }
        br.close();
        fr.close();
        //开始写入
        writeMethod(data);
    }

    /**
     * 查找字典
     */
    public static void searchDict() throws IOException {
        //读取test文件
        FileReader fr=new FileReader("E:\\microserve\\oj-backend-microservice-master (2)\\my-test\\src\\main\\java\\com\\test\\mytest\\file\\test.csv");
        BufferedReader br=new BufferedReader(fr);
        String line="";
        //结果集
        int count = 0;
        List<String[]> datas = new ArrayList<String[]>();
        while ((line=br.readLine())!=null) {
            if (count != 0) {
                String[] splits = line.split(",");
                for (String split: splits){
                    split = split.substring(1,split.length()-1);
                    System.out.println(split);
                }
                datas.add(splits);
            }
            count++;
        }
        System.out.println(datas);
        br.close();
        fr.close();
        //读取字典
        String fileName = "E:\\microserve\\oj-backend-microservice-master (2)\\my-test\\src\\main\\java\\com\\test\\mytest\\file\\标签词库1026.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 参数一：读取的excel文件路径
        // 参数二：读取sheet的一行，将参数封装在DemoData实体类中
        // 参数三：读取每一行的时候会执行DemoDataListener监听器
        DictDataListener listener = new DictDataListener();
        EasyExcel.read(fileName, Dict.class, listener).sheet().doRead();
        List<Dict> lists = listener.getList();
        //返回结果
        List<String[]> res = new ArrayList<>();
        //开始匹配
        for (String[] data:datas){

            if(data.length<3){
                continue;
            }
            String[] str =  new String[4];
            str[0] = data[0].substring(1,data[0].length()-1);
            str[1] = data[1].substring(1,data[1].length()-1);
            str[2] = data[2].substring(1,data[2].length()-1);
            for (Dict list:lists){

                if(data[2].contains(list.getKeyword1()) && data[2].contains(list.getKeyword2())){
                    str[3] = list.getTag();

                }
            }
            res.add(str);
        }
        writeMethod2(res);
    }

    public static String[] jsonAnalyze(String data){
        Pattern ptn = Pattern.compile("(.*?)storeName(.*?),(.*?)storeId(.*?),(.*?)");
        Matcher matcher = ptn.matcher(data);
        //
        String[] res = new String[3];
        if (matcher.matches()) {
            String a1 = matcher.group(2);
            String a2 = matcher.group(4);
            System.out.println("结果");
            System.out.println("----------------------------------------");
            System.out.println(data.split(",")[0]);
            String b1 = a1.substring(5,a1.length()-2);
            System.out.println(b1);
            String b2 = a2.substring(5,a2.length()-2);
            System.out.println(b2);
            //删除
            if (b2.charAt(b2.length()-1)=='\\'){
                b2 = b2.substring(2,b2.length()-1);
            }
            res[0] = data.split(",")[0];
            res[1] = b2;
            res[2] = b1;
        }

        return res;
    }



    public static void writeMethod(List<String[]> data){
        File file = new File("E:\\microserve\\oj-backend-microservice-master (2)\\my-test\\src\\main\\java\\com\\test\\mytest\\file\\test.csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // 写入
            List<String[]> res = new ArrayList<String[]>();
            res.add(new String[]{"task_id","storeId","storeName"});
            for (String[] strs: data){
                res.add(strs);
            }
            writer.writeAll(res);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMethod2(List<String[]> data){
        File file = new File("E:\\microserve\\oj-backend-microservice-master (2)\\my-test\\src\\main\\java\\com\\test\\mytest\\file\\result.csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // 写入
            List<String[]> res = new ArrayList<String[]>();
            res.add(new String[]{"task_id","storeId","storeName","tag"});
            for (String[] strs: data){
                res.add(strs);
            }
            writer.writeAll(res);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        //生成不带标签文件
        createCsv();
        //查字典加标签
        searchDict();
    }
}
