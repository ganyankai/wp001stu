package cn.dante.decortor;

public class ClientTest {
	public static void main(String[] args) {
		Component component = new ConcreteComponent(); 
		System.out.println("------装饰前：-------");
		component.function();

		Component newComponent = new ConcreteDecorator(component);
		System.out.println("------装饰后：-------");
		newComponent.function();

		System.out.println("------装饰后2：-------");
		Component component1 = new MyDecorator(newComponent);
		component1.function();

		System.out.println("------装饰后3：-------");
		Component component3 = new MyDecorator(component1);
		component3.function();
	}
}

