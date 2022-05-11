import java.io.FileNotFoundException;

public class Main {
  Memory memory ;
  Processor processor;
	public Main() {
		
		this.memory = new Memory();
		this.processor = new Processor(memory);
		
	}

	public static void main(String[] args) throws FileNotFoundException {
		Main main=new Main();
		codeParser pr = new codeParser(main.memory);
		pr.executer("src/test.txt");
		main.processor.pipeline();
	}

}
