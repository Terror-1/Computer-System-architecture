
public class testCase {
	public static void testRegisterLDI() {
		//test all the register exists
		for (int i = 0; i < 64; i++) {
			System.out.printf("LDI R%d 16", i);
			System.out.println();
		}
		// test the maximum and minimum values
		// registers can hold from -128 >127 but the immediate ranges from -32 , 31 so technically we can load in this range
		for (int i = -32; i < 32; i++) {
			for (int j = -128; j < 128; j++) {
				System.out.printf("LDI R%d %d", i, j);
				System.out.println();
			}

		}
	}
	public static void testInstructionMem() {
		for (int i = 0; i < 1024; i++) {
			System.out.println("LDI R0 1");
		}

	}

	public static void testDataMemory() {
		// we can reach from 0 -> 63 only adresses in memory as we have only 6 bits
		// immediate unsigned values
		System.out.println("LDI R0 15");
		for (int i = 0; i < 64; i++) {
			System.out.printf("SB R0 %d", i);
			System.out.println();
		}
		System.out.println("LB R0 0");
	}
	public static void testADD() {
		System.out.println("LDI R0 15");
		System.out.println("LDI R1 30");
		System.out.println("ADD R0 R1");
		System.out.println("SB R0 0");
	}
	public static void testSUB() {
		System.out.println("LDI R0 15");
		System.out.println("LDI R1 30");
		System.out.println("SUB R0 R1");
		System.out.println("SB R0 0");
	}
	public static void testMUL() {
		System.out.println("LDI R0 15");
		System.out.println("LDI R1 30");
		System.out.println("MUL R0 R1");
		System.out.println("SB R0 0");
	}
	public static void testAND() {
		System.out.println("LDI R0 15");
		System.out.println("LDI R1 30");
		System.out.println("AND R0 R1");
		System.out.println("SB R0 0");
	}
	public static void testOR() {
		System.out.println("LDI R0 15");
		System.out.println("LDI R1 30");
		System.out.println("OR R0 R1");
		System.out.println("SB R0 0");
	}
	public static void testSLC() {
		// 11111110 >>>> //11000000
		System.out.println("LDI R0 -2");
		System.out.println("SLC R0 5");
		System.out.println("SB R0 0");
		//test with postive value
		System.out.println("LDI R1 2");
		System.out.println("SLC R1 5");
		//2 * 2^5
		System.out.println("SB R1 1");
	}
	public static void testSRC() {
		System.out.println("LDI R0 12");
		System.out.println("SRC R0 2");
		// 12 / 2^2 
		System.out.println("SB R0 0");
		System.out.println("LDI R1 -12");
		System.out.println("SRC R1 2");
		// -12 / 2^2
		System.out.println("SB R1 1");
	}

	public static void main(String[] args) {
		testSLC();
	}
}
