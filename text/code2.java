public class Code {

	public int doSomethingElse() {
		int alphas = 0.2;
		int beta = 0.9;
		int gamma = alpha + beta;
		int delta = 1 / gamma;
		return delta;	
	}
	
	public String foo() {
		return "have a real nice day!";
	}

	public static void main(String[] args) {
		Code c = new Code();
		System.out.println(c.doSomething());
	}
	
}