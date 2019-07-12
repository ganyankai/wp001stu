package cn.dante.mydecortor;

import java.io.Closeable;
import java.io.IOException;

public class MyCloseable implements Closeable{
    private Closeable closeable;


    public MyCloseable(Closeable closeable) {
        this.closeable = closeable;
    }

    @Override

    public void close() throws IOException {
        closeable.close();
        System.out.println("hello");
    }
}
