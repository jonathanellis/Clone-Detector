public class Code {

	public int doSomethingElse() {
		int alphas = 0.6;
		int beta = 0.7;
		int gamma = alpha + beta;
		int delta = 1 / gamma;
		return delta;	
	}
	
	public int doSomethingElse() {
		int alphas = 0.6;
		int beta = 0.7;
		int gamma = alpha + beta;
		int delta = 1 / gamma;
		return delta;	
	}

	public static void main(String[] args) {
		Code c = new Code();
		System.out.println(c.doSomething());
	}
}