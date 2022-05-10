
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
}
