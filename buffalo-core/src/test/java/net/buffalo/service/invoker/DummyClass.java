package net.buffalo.service.invoker;

public class DummyClass {
	public int sum(int a, int b) {
		return a+b;
	}
	
	public double sum(double a, double b) {
		return a+b;
	}
	
	public String ex() {
		throw new RuntimeException("just throw out");
	}
	
	public void voidFunc() {
		System.out.println("Called void Func");
	}
}
