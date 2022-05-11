public class Main {
  Memory memory ;
  Processor processor;
  ALU ALU;
	public Main() {
		this.ALU= new ALU();
		this.memory = new Memory();
		this.processor = new Processor(memory,ALU );
		
	}

	public static void main(String[] args) {
		Main m=new Main();
		m.processor.pipeline();
	}

}
