package cn.numsingle;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Show {

    //先打印输入输出
    public static void main(String[] args) throws Exception {
        int[][] arr = new int[9][9];
//        print(arr);
//        System.out.println(Arrays.toString(arr));
//        dataCollect();
//        new Show().dataCollect(arr);
//        print(arr);
//        new Show().printLine(arr,8);
//        new Show().printPlace(arr,5);

        new Show().dataCollect(arr);
//        new Show().remainNumLine(arr,0);
//        new Show().remainNumCol(arr,8);
//        new Show().remainNumPlace(arr,9);

        List<Integer> list = new Show().remainNumCell(arr, 8, 0);
        System.out.println("list:"+list);
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

//    @Test
    public void printPlace(int arr[][],int place) throws Exception {
//    public void printPlace() throws Exception {
//        int[][] arr = new int[9][9];
//        print(arr);
//        System.out.println(Arrays.toString(arr));
//        dataCollect();
//        new Show().dataCollect(arr);
//        int col = 0;

//        int place = 1; //0-2 line; 0-2 col

        int lineStart = (place-1)/3*3;
        int lineEnd = lineStart+3;

        int colStart = ((place-1)%3)*3;
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

    //计算每一宫的剩余数字
//    @Test
    public List<Integer> remainNumPlace(int arr[][],int place){
//        生成一个list
        List<Integer> listOri =  Arrays.asList(1,2,3,4,5,6,7,8,9);

        List<Integer> list =  new ArrayList<>();
        list.addAll(listOri);

        int lineStart = (place-1)/3*3;
        int lineEnd = lineStart+3;

        int colStart = ((place-1)%3)*3;
        int colEnd = colStart + 3;
        for (int i=lineStart;i<lineEnd;i++){
//            System.out.println();
            for (int j = colStart;j<colEnd;j++){
                if(list.contains(arr[i][j])) {
                    list.remove(list.indexOf(arr[i][j]));
                }
            }
        }

        System.out.println(list);
        return list;
    }


    //计算每一行的剩余数字 todo
//    @Test
    public List<Integer> remainNumLine(int arr[][],int line){
//        生成一个list
        List<Integer> listOri =  Arrays.asList(1,2,3,4,5,6,7,8,9);

        List<Integer> list =  new ArrayList<>();
        list.addAll(listOri);
        for (int j=0;j<arr[line].length;j++){
            if(list.contains(arr[line][j])){
                list.remove(list.indexOf(arr[line][j]));
            }
        }

        System.out.println(list);
        return list;
    }

    public List<Integer> remainNumCol(int arr[][],int col){
//        生成一个list
        List<Integer> listOri =  Arrays.asList(1,2,3,4,5,6,7,8,9);

        List<Integer> list =  new ArrayList<>();
        list.addAll(listOri);
        for (int i=0;i<arr.length;i++){
            if(list.contains(arr[i][col])){
                list.remove(list.indexOf(arr[i][col]));
            }
        }

        System.out.println(list);
        return list;
    }

    //计算每一单元格可能的数字
    public List<Integer> remainNumCell(int arr[][],int row,int col) {
        if(arr[row][col]!=0){
            List<Integer> listOne = new ArrayList<>();
            listOne.add(arr[row][col]);
            return listOne;
        }

//        生成一个list
        List<Integer> listOri = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> list = new ArrayList<>();
        list.addAll(listOri);

        List<Integer> remainLineList = remainNumLine(arr, row);
        List<Integer> remainColList = remainNumCol(arr, col);
        int place = lineColToPlace(row+1,col+1);

        List<Integer> remainNumPlaceList = remainNumPlace(arr,place);

        //todo 求交集
        remainLineList.retainAll(remainColList);
        remainLineList.retainAll(remainNumPlaceList);

        return remainLineList;
    }


    @Test
    public void testList(){
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> list = new ArrayList<>();
        list.addAll(list1);
        list.remove(9);
        list.add(12);
        System.out.println(list);
        list.add(10);
//        list.remove(1);
        System.out.println(list);
    }


    public static int lineToOne(int line){
        return (line-1)/3+1;
    }

    //根据行和列求出所在的宫
    public static int lineColToPlace(int line,int col){
        return (lineToOne(line)-1)*3+lineToOne(col);
    }
}
