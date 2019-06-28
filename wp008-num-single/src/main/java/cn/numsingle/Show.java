package cn.numsingle;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class Show {

    //先打印输入输出
    public static void main(String[] args) throws Exception {
        int[][] arr = new int[9][9];
//        print(arr);
//        System.out.println(Arrays.toString(arr));
//        dataCollect();
        new Show().dataCollect(arr);
//        print(arr);
        new Show().printLine(arr,8);
    }

    public static void print(int arr[][]){

        for (int i=0;i<arr.length;i++){
            System.out.println();
            for (int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
        }
    }

    public static void printCommom(int arr[][]){
        for (int i=0;i<arr.length;i++){
            System.out.println();
            for (int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
        }
    }


    //数据录入
//    @Test
    public void dataCollect(int arr[][]) throws Exception {
//        int arr[][] = new int[9][9];
        Resource resource = new ClassPathResource("num.txt");
        File sourceFile =  resource.getFile();

        BufferedReader br = new BufferedReader(new FileReader(sourceFile));
        String line = "";
        int lineNum = 0;
        while((line = br.readLine()) != null) {
            String[] strs = line.split(" ");
            for (int i = 0; i < strs.length; i++) {
                arr[lineNum][i] = Integer.parseInt(strs[i]);
            }
            lineNum++;
        }
        br.close();
//        print(arr);
    }


//    @Test
//    public void printLine(int arr[][],int line) throws Exception {
    public void printLine(int[][] arr,int line) throws Exception {
//        int[][] arr = new int[9][9];
////        print(arr);
////        System.out.println(Arrays.toString(arr));
////        dataCollect();
//        new Show().dataCollect(arr);
//        int line = 8;

        System.out.println(Arrays.toString(arr[line]));
    }


    @Test
//    public void printCol(int arr[][],int col) throws Exception {
    public void printCol() throws Exception {
        int[][] arr = new int[9][9];
//        print(arr);
//        System.out.println(Arrays.toString(arr));
//        dataCollect();
        new Show().dataCollect(arr);
        int col = 0;

        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i][col]);
        }

    }

    @Test
//    public void printPlace(int arr[][],int place) throws Exception {
    public void printPlace() throws Exception {
        int[][] arr = new int[9][9];
//        print(arr);
//        System.out.println(Arrays.toString(arr));
//        dataCollect();
        new Show().dataCollect(arr);
//        int col = 0;

        int place = 1; //0-2 line; 0-2 col

        int lineStart = place/3;
        int lineEnd = lineStart+3;
        int colStart = (place%3)*2 -1 ;
        int colEnd = colStart + 3;

        for (int i=lineStart;i<lineEnd;i++){
            System.out.println();
            for (int j = colStart;j<colEnd;j++){
                System.out.print(arr[i][j]+" ");
            }
        }

    }

    @Test
    public void test() throws Exception {
        System.out.println(1/3);
    }

}
