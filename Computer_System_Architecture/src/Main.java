import java.io.FileNotFoundException;
import java.util.Random;

public class Main {
  Memory memory ;
  Processor processor;
  codeParser pr ;
  
	public Main() throws FileNotFoundException {
		
		this.memory = new Memory();
		this.pr = new codeParser(this.memory);
	    this.pr.executer("src/test.txt");
		this.processor = new Processor(memory,this.pr.getNoInst()); 
		
	}

	public static void main(String[] args) throws FileNotFoundException{
		Random random= new Random();
		Main main=new Main();
		/*for(int i=0 ; i<main.memory.getDataMemory().length;i++) {
			main.memory.getDataMemory()[i]=(byte)random.nextInt(100);
		}*/
		System.out.println("--------------------------START-----------------------------------");
		main.processor.pipeline();
		main.processor.printRegister();
		main.processor.printSREGRegister();
		main.memory.printdataMem();
		main.memory.printInstMem();
		System.out.println("--------------------------END-------------------------------------");
	}

}
