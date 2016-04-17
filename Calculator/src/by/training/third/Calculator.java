package by.training.third;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
	
private static BigDecimal firstArg;
private static BigDecimal secondArg;

/*	Напишите «калькулятор», который принимает из командной строки два числа 
	и выводит в консоль их сумму, разность, произведение и частное.*/

	public static void main(String[] args) {
		
		String message = "Program needs three arguments. For example \"456 + 325\" or \"421.25 add 123.33\".\n"
				+ "Second argument is operation that needs one symbol \"+-/*\" or string \"add/subtract/divide/multiply\".\n"
				+ "Dont forget to escape symbols like / or * in your implementation of commandline or replace them "
				+ "with \"divide\" and \"multiply\" strings.";
		
		final int rounding = 3;
		
		
		if (args.length != 3) {
			
			System.err.println(message);
			System.exit(0);
			
		}
		
		
		try {
			
			firstArg = new BigDecimal(args[0]);
			secondArg = new BigDecimal(args[2]);
			
		} catch (NumberFormatException e) {
			
			System.err.println("Incorrect operands input!");
			System.err.println(message);
			System.exit(0);
			
		}
		
		
		if ((args[1].charAt(0) == '+' && args[0].length() == 1) || args[1].equals("add")) {
		
			System.out.println(firstArg.add(secondArg).toString());
			
		}else if ((args[1].charAt(0) == '-' && args[0].length() == 1) || args[1].equals("subtract")) {
			
			System.out.println(firstArg.subtract(secondArg).toString());
			
		}else if ((args[1].charAt(0) == '/' && args[0].length() == 1) || args[1].equals("divide")) {
			
			/*Check division by zero*/
			if(secondArg.compareTo(BigDecimal.ZERO) == 0) {
				System.err.println("Division by zero");
				System.exit(0);
			}
			
			/*Rounding for 1/3 cases*/
			System.out.println(firstArg.divide(secondArg, rounding, RoundingMode.HALF_UP).toString());
			
		}else if ((args[1].charAt(0) == '*' && args[0].length() == 1) || args[1].equals("multiply")) {
			
			System.out.println(firstArg.multiply(secondArg).toString());
			
		}else{
			
			System.err.println("Incorrect operation input!");
			System.err.println(message);
			
		}

	}

}
