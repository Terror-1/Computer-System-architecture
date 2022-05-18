import java.util.Arrays;

public class Memory {
	private byte [] dataMemory ;
	private short [] instMemory ;

	public Memory() {
		this.dataMemory=new byte[2048];
		this.instMemory = new short[1024];
		
	}
	public byte[] getDataMemory() {
		return dataMemory;
	}
	public short[] getInstMemory() {
		return instMemory;
	}
	public void printInstMem() {
		System.out.println("Contet of instruction Memory :");
		System.out.println(Arrays.toString(this.instMemory));
		System.out.println("------------------------------------------------------------------");
	}
	public void printdataMem() {
		System.out.println("Contet of data Memory :");
		System.out.println(Arrays.toString(this.dataMemory));
		System.out.println("------------------------------------------------------------------");
	}
}
