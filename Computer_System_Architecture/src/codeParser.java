import java.io.*;
import java.util.*;

public class codeParser {
    Queue<String> Instructions;
	public codeParser() {
		this.Instructions=new LinkedList<>();
	}
	public void executer(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			String st = sc.nextLine();
			this.Instructions.add(st);
		}
		while(!Instructions.isEmpty()) {
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
			case "LP": opCode=10;	break;
			case "SP": opCode=11;	break;
			default:break;
			}
			int arg1= Integer.parseInt(token[1].substring(1));
			int arg2=0;
			if (opCode==3 || opCode==4 || opCode==8 ||opCode==9 ||opCode==10 ||opCode==11 ){
				arg2= Integer.parseInt(token[2]);
			}
			
			else {
				Integer.parseInt(token[2].substring(1));
			}
			
			//edit by masking bits
//			String opString = Integer.toBinaryString(opCode);
//			while(!(opString.length()== 4)) {
//				opString='0'+opString;
//			}
//			String arg1String = Integer.toBinaryString(arg1);
//			while(!(arg1String.length()== 6)) {
//				arg1String='0'+arg1String;
//			}
//			String arg2String = Integer.toBinaryString(arg2);
//			while(!(arg2String.length()== 4)) {
//				arg2String='0'+arg2String;
//			}
//			String temp = opString+arg1+arg2String;

			
			
			
		}
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		
	}

}
