package cn.dante.mydecortor;

public class MyDecortor implements Animal{

    private Animal animal;

    public MyDecortor(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void run() {
        animal.run();
        System.out.println("飞快的跑");
    }
}
