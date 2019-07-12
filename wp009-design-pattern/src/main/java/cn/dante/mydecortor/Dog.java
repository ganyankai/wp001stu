package cn.dante.mydecortor;

public class Dog implements Animal{
    @Override
    public void run() {
        System.out.println("狗在跑");
    }
}
