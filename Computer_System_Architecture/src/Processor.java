
public class Processor {
    private byte[] Registers; 
    private short programCounter;
    private boolean[] statusRegister;
    
	public Processor() {
		this.Registers= new byte[64];
		this.statusRegister = new boolean[8];
		this.programCounter=0;
		
	}

}
