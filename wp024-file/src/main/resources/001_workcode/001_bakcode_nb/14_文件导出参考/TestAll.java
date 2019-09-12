package com.zrytech.framework.mytest;

import com.alibaba.fastjson.JSON;
import com.zrytech.framework.app.constants.MessageConstant;
import com.zrytech.framework.common.util.DictionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestAll{
    @Test
    public void test5() throws Exception{
        List<String> titles = new ArrayList<>();
        titles.add("姓名");
        titles.add("性别");

        List<PageData> varList  = new ArrayList<>();
        PageData vpd = new PageData();
        vpd.put("var1","zhangsan");
        vpd.put("var2","nan");
        varList.add(vpd);

        PageData vpd2 = new PageData();
        vpd2.put("var1","zhangsan2");
        vpd2.put("var2","nan2");
        varList.add(vpd2);

        toExcel.toExcel(titles,varList);
    }

    @Test
    public void test4() throws Exception{
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        //创建HSSFRow对象
        HSSFRow row = sheet.createRow(0);
        //创建HSSFCell对象
        HSSFCell cell=row.createCell(0);
        //设置单元格的值
        cell.setCellValue("单元格中的中文");

        row = sheet.createRow(1);
        cell=row.createCell(0);
        cell.setCellValue("hello world");


        //输出Excel文件
        FileOutputStream output=new FileOutputStream("d:\\workbook_my.xls");
        wb.write(output);
        output.flush();
    }


    @Test
    public void test3(){
        String contentCN = DictionUtil.getValue(MessageConstant.MESSAGE_CONTENT,  MessageConstant.MESSAGE_SECKILL_ORDER);
        String[] strArr = new String[]{""};
        contentCN = String.format(contentCN,strArr);
        System.out.println(contentCN);
    }

    @Test
    public void test2() throws Exception{
//        String contentCN = DictionUtil.getValue(MessageConstant.MESSAGE_CONTENT, "message_group_order");
//        Object[] strarr = new Object[]{"小超","是个","大帅哥"};
        String[] strarr = new String[]{"小超","是个","大帅哥"};
//        String str=String.format("Hi,%s %s %s", "小超","是个","大帅哥","sss");
        String str=String.format("Hi,%s %s %s", strarr);
        System.out.println(str);
//        System.out.println(contentCN);
    }

    @Test
    public void test() throws Exception{
        try{

            JpushUtil.jpush(null,"1","2");
//            int i= 1/0;
//            testException();
        }catch (Exception e){
            log.error("捕获成功");
            log.error(JSON.toJSONString(e));
        }
        System.out.println("over");
        System.out.println("over1");
        System.out.println("over2");
    }

    public void testException(){
        try{
            int i= 1/0;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }


}
