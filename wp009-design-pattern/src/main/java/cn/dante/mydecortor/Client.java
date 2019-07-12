package cn.dante.mydecortor;

import java.io.Closeable;
import java.io.IOException;

public class Client {

    public static void main(String[] args) throws Exception{
//        Animal animal = new Dog();
//        animal.run();
//
//        System.out.println("装饰后:");
//        MyDecortor myDecortor = new MyDecortor(animal);
//        myDecortor.run();

        Closeable closeable = new Closeable() {
            @Override
            public void close() throws IOException {
                System.out.println("in close");
            }
        };

        MyCloseable myCloseable = new MyCloseable(closeable);
        myCloseable.close();

    }

}
