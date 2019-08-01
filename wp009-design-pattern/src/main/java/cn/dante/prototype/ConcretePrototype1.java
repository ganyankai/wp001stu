package cn.dante.prototype;

public class ConcretePrototype1 extends Prototype{
    public ConcretePrototype1(String id) {
        super(id);
    }

    @Override
    public Prototype Clone() throws Exception{
        return (Prototype)this.clone();
    }
}
