import java.util.Arrays;

public class Processor {
    private byte[] registerFile; 
    private short PC;
    private boolean[] statusRegister;
	private Memory memory;
	private boolean[] flag;
	private byte operandA,operandB,immediate,opCode,R1,R2;
	private short instruction;
	private int noOfinstructions=0;

	public Processor(Memory memory,int noOfInst) {
		this.registerFile= new byte[64];
		this.statusRegister = new boolean[8]; //z >0 // S >1 // N >2 // V >3 // C >4
		this.PC=0;
		this.memory=memory;
		this.flag = new boolean[3]; //execute[0] decode[1] fetch[2]
		this.noOfinstructions=noOfInst;
	}

	public void pipeline(){
		int cycles = 1;
		while (true) {
			if(PC<noOfinstructions)
				flag[2] = true;
			if ((!flag[0]) && (!flag[1]) && (!flag[2])) {
				break;
			}
			System.out.println("Start of cycle "+cycles);
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

			System.out.println("**************************END OF THE CYCLE***********************************");
		}
	}
	public void fetch() {

		instruction = memory.getInstMemory()[PC];
		System.out.println("--The processor is currently fetching instrction "+PC);
		System.out.println("The input parameter for Fetching is pc: "+PC);
		System.out.println("The output instruction fetched is "+ binaryPrint(Integer.toBinaryString(instruction), 16) );
		System.out.println("------------------------------------------------------------------");
		this.PC++;

	}

	public void decode(short instruction){
		
     opCode = (byte) ((instruction&  0b1111000000000000 )>> (12)  );
		 R1 = (byte) ((instruction & 0b0000111111000000)>>(6));
		 R2 = (byte) ((instruction & 0b0000000000111111));
	     operandA = registerFile[R1];
		 operandB = registerFile[R2];
		 immediate = R2;
		 System.out.println("--The processor is currently decoding instrction "+ (PC-1));
		 System.out.println("The input parameter for decoding is instuction "+binaryPrint(Integer.toBinaryString(instruction), 16));
		 System.out.println("The output decoded is : ");
		 System.out.println("opCode is "+binaryPrint(Integer.toBinaryString(opCode), 4));
		 System.out.println("Frist register address  is " + binaryPrint(Integer.toBinaryString(R1), 6) + " / First register content is "+ operandA);
		 System.out.println("Second register address is " + binaryPrint(Integer.toBinaryString(R2), 6)+" / Second register content is "+ operandB);
		 System.out.println("immediate value  is " + binaryPrint(Integer.toBinaryString(R2), 6));
		 System.out.println("------------------------------------------------------------------");
	}

	public void execute() {

		System.out.println("--The processor is currently executing instrction "+ (PC-2));
		System.out.println("The input parameters for executing is : ");
		System.out.println("opCode is "+binaryPrint(Integer.toBinaryString(opCode), 4));
		System.out.println("Frist register content  is " + binaryPrint(Integer.toBinaryString(R1), 6));
		System.out.println("Second register content is " + binaryPrint(Integer.toBinaryString(R2), 6));
		System.out.println("immediate value is " + binaryPrint(Integer.toBinaryString(R2), 6));
		byte tempRegister = registerFile[R1];
		registerFile[R1] = run(operandA, operandB,immediate, opCode, statusRegister, PC);
		if(tempRegister!=registerFile[R1]) {
			System.out.println("Register " + R1 +" content is updated to "+registerFile[R1]);
		}
		System.out.println("------------------------------------------------------------------");
		

	}

	public byte run(byte operandA,byte operandB,byte imm,byte opCode,boolean[]SREG,short pc) {
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
				System.out.println("The operation currently executed is ADDING ---> output in decimal : "+temp);
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
				System.out.println("The operation currently executed is SUBTRACTING ---> output in decimal : "+temp);
				break;
			case 2:
				//MUL
				temp=(operandA*operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				System.out.println("The operation currently executed is MULTIPLYING ---> output in decimal : "+temp);
				break;
			case 3:
				//LDI
				temp=imm;
				System.out.println("The operation currently executed is LOADING ---> First register updated content in decimal : "+temp);
				break;
				
				case 4 :
				//BEQZ
				int pcINT=0;
				if (operandA==0)
					pcINT=pc+1+imm;
				pc = (short)pcINT;
				System.out.println("The operation currently executed is BEQZ ---> pc changed to  : "+pc);
				break;
			case 5 :
				//AND
				temp = (operandA & operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				System.out.println("The operation currently executed is ANDING ---> output in decimal : "+temp);
				break;
			case 6 :
				//OR
				temp = (operandA | operandB);
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				System.out.println("The operation currently executed is ORING ---> output in decimal : "+temp);
				break;
			case 7 :
				//JR
				pc =(short) ((operandA <<8)|operandB);
				System.out.println("The operation currently executed is JR ---> pc changed to  : "+pc);
				break;
			case 8 :
				//SLC
				temp = (operandA << imm) |(operandA >>(8-imm));
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				System.out.println("The operation currently executed is SLC ---> output in decimal : "+temp);
				break;
			case 9 :
				//SRC
				temp = (operandA >> imm) |(operandA << (8-imm));
				// check zero flag
				SREG[0]=(temp==0);
				// check negative flag
				SREG[2]=(temp<0);
				System.out.println("The operation currently executed is SRC ---> output in decimal : "+temp);
				break;
			case 10:
				//LB
				temp = memory.getDataMemory()[imm];
				System.out.println("The operation currently executed is LOADBYTE ---> First register is loaded with : "+temp+ " in decimal");
				break;

			case 11:
				//SB
				memory.getDataMemory()[imm]=operandA;
				System.out.println("The operation currently executed is STOREBYTE ---> data memory at index "+binaryPrint(Integer.toBinaryString(imm), 6) +"is updated to "+temp +" in decimal");
				break;
			default:
				break;

		}
		return (byte)temp;
	}
	public static String binaryPrint(String arg1,int totalNumberOfbits  ) {
		while(arg1.length()<totalNumberOfbits) {
			arg1= arg1.charAt(0)+arg1;
		}
		String tempString = arg1.substring(arg1.length()-totalNumberOfbits,arg1.length());
		return tempString;
	}
	public void printRegister() {
		System.out.println("Contet of Register File :");
		System.out.println(Arrays.toString(this.registerFile));
		System.out.println("------------------------------------------------------------------");
		
	}
	public void printSREGRegister() {
		System.out.println("Contet of status Register File :");
		System.out.print("[0,0,0,");
		for(int i = 3 ;i<this.statusRegister.length;i++) {
			 if(i==(this.statusRegister.length-1))  System.out.print(this.statusRegister[i]?"1]":"0]");
			 else System.out.print(this.statusRegister[i]?"1,":"0,");
			 
			 }
		System.out.println();
		System.out.println("------------------------------------------------------------------");
	}
	
	public static void main(String[] args) {
		
	}
}
