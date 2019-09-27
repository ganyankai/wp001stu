package mytest;

public class TestAll {

    public static void main(String[] args) {
        int line = 4;
        int col = 3;
//        System.out.println(((line-1)/3+1-1)*3+((col-1)/3+1-1));
//        System.out.println(lineToOne(3));
        System.out.println((lineToOne(line)-1)*3+lineToOne(col));

    }

    public static int lineToOne(int line){
        return (line-1)/3+1;
    }

    public static int lineColToPlace(int line,int col){
        return (lineToOne(line)-1)*3+lineToOne(col);
    }
}
