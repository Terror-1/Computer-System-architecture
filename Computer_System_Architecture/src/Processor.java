public class Processor {
    private byte[] registerFile; 
    private short PC;
    private boolean[] statusRegister;
	private Memory memory;
	private boolean[] flag;
	private byte operandA,operandB,opCode,R1,R2;
	private short instruction;
	private int cycles;
	
	public Processor(Memory memory) {
		this.registerFile= new byte[64];
		this.statusRegister = new boolean[8]; //z >0 // S >1 // N >2 // V >3 // C >4
		this.PC=0;
		this.memory=memory;
		this.flag = new boolean[3]; //execute[0] decode[1] fetch[2]
	}

	public void pipeline(){

		int noOfinstructions=0;
		for(Object el: memory.getInstMemory()) { if(el != null) noOfinstructions++; }

		while (true) {
			if(PC<noOfinstructions)
				flag[2] = true;
			if (flag[0] && flag[1] && flag[2]) {
				break;
			}
			System.out.println("Cycles "+cycles);
			cycles++;
			System.out.println();
			if(flag[0]) //execute
			{
				execute();
				flag[0] = false;
			}
			if(flag[1]) //decode
			{
				decode(instruction);
				flag[1] = false;
				flag[0] = true;
			}
			if(flag[2]) //fetch
			{
				fetch();
				flag[2] = false;
				flag[1] = true;
			}

		}
	}
	public void fetch() {

		instruction = memory.getInstMemory()[PC];
		this.PC++;

	}

	public void decode(short instruction){

	     opCode = (byte) (instruction >> 12);
		 R1 = (byte) ((instruction << 4) >> 10);
		 R2 = (byte) (instruction & 0b0000000000111111);
	     operandA = registerFile[R1];
		 operandB = registerFile[R2];
        //		System.out.println(opCode + " " + R1 + " " + R2);
	}

	public void execute() {

		registerFile[R1] = run(operandA, operandB, opCode, statusRegister, PC);


	}

	public byte run(byte operandA,byte operandB,byte opCode,boolean[]SREG,short pc) {
		int temp=operandA;
		switch (opCode) {
			case 0:
				//ADD
				temp=(operandA+operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check carry flag
				SREG[4]= ((temp & (1<<8)) !=0) ;
				// check negative flag
				SREG[2]=(temp<0);
				// check overflow flag
				SREG[3]= ((operandA & (1<<7)) == (operandB &(1<<7))) & ((operandA & (1<<7)) != (temp &(1<<7)));
				// check sign flag
				SREG[1]=SREG[3] ^ SREG[2];
				break;
			case 1:
				//SUB
				temp=(operandA-operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				// check overflow flag
				SREG[3]= ((operandA & (1<<7)) != (operandB &(1<<7))) & ((operandB & (1<<7)) == (temp &(1<<7)));
				// check sign flag
				SREG[1]=SREG[3] ^ SREG[2];
				break;
			case 2:
				//MUL
				temp=(operandA*operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				break;
			case 3:
				//LDI
				temp=operandB;
				break;
			case 4 :
				//BEQZ
				int pcINT=0;
				if (operandA==0)
					pcINT=pc+1+operandB;
				pc = (short)pcINT;
				break;
			case 5 :
				//AND
				temp = (operandA & operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				break;
			case 6 :
				//OR
				temp = (operandA | operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				break;
			case 7 :
				//JR
				pc =(short) ((operandA <<8)|operandB);
				break;
			case 8 :
				//SLC
				temp = (operandA << operandB) |(operandA >>(8-operandB));
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				break;
			case 9 :
				//SRC
				temp = (operandA >> operandB) |(operandA << (8-operandB));
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				break;
			case 10:
				//LB
				temp = memory.getDataMemory()[operandB];
				break;

			case 11:
				//SB
				memory.getDataMemory()[operandB]=operandA;
				break;
			default:
				break;

		}
		return (byte)temp;
	}
}
