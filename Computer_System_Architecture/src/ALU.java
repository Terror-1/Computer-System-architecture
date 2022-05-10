public class ALU {
    // Z > 0 -- S > 1 -- N >2 -- V >3 -- C >4
	public ALU() {	
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
		default:
			break;
		}
		return (byte)temp;
	}

}
