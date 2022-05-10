import java.io.FileNotFoundException;

public class Processor {
    private byte[] registerFile; 
    private short PC;
    private boolean[] statusRegister;
    private ALU ALU;
    
	public Processor() {
		this.registerFile= new byte[64];
		this.statusRegister = new boolean[8];
		this.PC=0;		
		this.ALU=new ALU();
	}
//	public void fetch() {
//		ALU.run(0, 0, 0, statusRegister, PC);
//	}
//	
	public static void main(String[] args) throws FileNotFoundException {
		Memory memory = new Memory();
		codeParser pr = new codeParser(memory);
		pr.executer("src/test.txt");
		System.out.println(memory.getInstMemory()[0]+" "+memory.getInstMemory()[1]);

	}
}
