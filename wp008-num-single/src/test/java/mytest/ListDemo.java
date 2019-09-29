package mytest;

import java.util.ArrayList;
import java.util.List;

public class ListDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<String> sList = new ArrayList<String>();
        List<String> sList2 = new ArrayList<String>();
        sList.add("1");
        sList.add("2");
        sList.add("3");
        sList.add("4");

        sList2.add("3");
        sList2.add("4");
        sList2.add("5");

        sList.retainAll(sList2);

        for (String s : sList) {
            System.out.println(s);
        }
    }

}

