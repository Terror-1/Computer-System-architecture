import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {
  private Memory memory ;
  private Processor processor;
  private codeParser pr ;
  private String filePath="src/test.txt";
  
	public Main(){
		
		this.memory = new Memory();
		this.pr = new codeParser(this.memory);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Main main=new Main();
		/*Random random= new Random();
		 * test random values in memory
		 *for(int i=0 ; i<main.memory.getDataMemory().length;i++) {
			main.memory.getDataMemory()[i]=(byte)random.nextInt(100);
		}*/
		System.out.println("Please enter the file path ** e.g. > src/...");
		//Scanner sc = new Scanner(System.in);
		//main.filePath = sc.nextLine();
		main.pr.executer(main.filePath);
		main.processor = new Processor(main.memory,main.pr.getNoInst()); 
		System.out.println("--------------------------START-----------------------------------");
		main.processor.pipeline();
		System.out.println("------------------------------------------------------------------");
		main.processor.printRegister();
		main.processor.printSREGRegister();
		main.memory.printdataMem();
		main.memory.printInstMem();
		System.out.println("--------------------------END-------------------------------------");
	}

}
