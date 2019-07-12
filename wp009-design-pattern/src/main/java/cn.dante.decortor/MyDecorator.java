package cn.dante.decortor;

public class MyDecorator extends Decorator{

    public MyDecorator(Component component) {
        super(component);
    }

    @Override
    public void function() {
        super.function();

        System.out.println("觉醒");
    }
}
