import java.io.*;
import java.util.*;

public class codeParser {
    private Queue<String> Instructions;
    private Memory memory;
    private int noInst=0;
	public int getNoInst() {
		return noInst;
	}
	public codeParser(Memory memory) {
		this.memory=memory;
		this.Instructions=new LinkedList<>();
	}
	public void executer(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			String st = sc.nextLine();
			this.Instructions.add(st);
			noInst++;
		}
		for(int i=0 ; !Instructions.isEmpty();i++) {
			String getInst = Instructions.poll();
			String token[] = getInst.split(" ");
			int opCode=0;
			switch (token[0].toUpperCase()) {
			case "ADD": opCode=0;	break;
			case "SUB": opCode=1;	break;
			case "MUL": opCode=2;	break;
			case "LDI": opCode=3;	break;
			case "BEQZ": opCode=4;	break;
			case "AND": opCode=5;	break;
			case "OR": opCode=6;	break;
			case "JR": opCode=7;	break;
			case "SLC": opCode=8;	break;
			case "SRC": opCode=9;	break;
			case "LB": opCode=10;	break;
			case "SB": opCode=11;	break;
			default:break;
			}
			int arg1= Integer.parseInt(token[1].substring(1));
			int arg2=0;
			if (opCode==3 || opCode==4 || opCode==8 ||opCode==9 ||opCode==10 ||opCode==11 ){
				arg2= Integer.parseInt(token[2]);
			}
			else {
				arg2=Integer.parseInt(token[2].substring(1));
			}
			//0000 0000 0000 0000 0000 0000 0000 0000  //0000 0000 0000 0000 0000 0000 0000 0001 // //0000 0000 0000 0000 0000 0000 0000 0010
			short inst = (short) ((((opCode << 6) | arg1 ) << 6)|arg2);


			memory.getInstMemory()[i]=inst;
		}	
	}
}
