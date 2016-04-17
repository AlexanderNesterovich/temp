package by.training.fourth;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Quadratic {
	
	private static BigDecimal firstArg;
	private static BigDecimal secondArg;
	private static BigDecimal thirdArg;

	public static void main(String[] args) {
		
		/*Ќайти действительные корни квадратного уравнени€ ax2 + bx + c = 0. 
		  орректно обработать случай, когда дискриминант равен нулю.*/
		
		final int rounding = 3;
		
		if (args.length != 3) {
			
			System.err.println("Program needs three arguments!");
			System.exit(0);
			
		}
		
		try {
			
			firstArg = new BigDecimal(args[0]);
			secondArg = new BigDecimal(args[1]);
			thirdArg = new BigDecimal(args[2]);
			
		} catch (NumberFormatException e) {
			
			System.err.println("Incorrect arguments input! Example: 1 2 3; 4123 123123 123123123; 0.5 0.1 0.3; 0 1 1 etc");
			System.exit(0);
			
		}
		
		BigDecimal disc;
		
		/*a = 0 and deeper*/
		if (firstArg.compareTo(BigDecimal.ZERO) == 0) {
			if (secondArg.compareTo(BigDecimal.ZERO) != 0) {
				
				System.out.println("x = " + thirdArg.negate().divide(secondArg, rounding, RoundingMode.HALF_UP).toString());
				System.exit(0);
				
			}else if (thirdArg.compareTo(BigDecimal.ZERO) == 0){
				
				System.out.println("x = any number");
				System.exit(0);
				
			}else{
				
				System.out.println("No roots!");
				System.exit(0);
				
			}
			
		}
		
		disc = secondArg.pow(2).subtract(firstArg.multiply(thirdArg).multiply(new BigDecimal(4)));
		
		
		int compare = disc.compareTo(BigDecimal.ZERO);
		
		/* Full cycle*/
		if(compare > 0) {
			
			/*bigSqrt is custom sqrt because java doesnt have sqrt for bigdecimal*/
			BigDecimal rootOfDisc = bigSqrt(disc);
			BigDecimal doubleA = firstArg.multiply(new BigDecimal("2"));
			
			BigDecimal resultOne = secondArg.negate().add(rootOfDisc).divide(doubleA, rounding, RoundingMode.HALF_UP);
			BigDecimal resultTwo = secondArg.negate().subtract(rootOfDisc).divide(doubleA, rounding, RoundingMode.HALF_UP);
			
			System.out.println("x1 = " + resultOne.toString());
			System.out.println("x2 = " + resultTwo.toString());
			
		}else if(compare == 0) {
			
			System.out.println("x1 = x2 = " + secondArg.negate().divide(firstArg.multiply(new BigDecimal(2)), rounding, RoundingMode.HALF_UP));
						
		}else{
			
			System.out.println("No roots!");
			
		}

	}
	
	
	private static final BigDecimal SQRT_DIG = new BigDecimal(150);
	private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

	
	/**
	 * Private utility method used to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	private static BigDecimal sqrtNewtonRaphson  (BigDecimal c, BigDecimal xn, BigDecimal precision){
	    BigDecimal fx = xn.pow(2).add(c.negate());
	    BigDecimal fpx = xn.multiply(new BigDecimal(2));
	    BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(),RoundingMode.HALF_DOWN);
	    xn1 = xn.add(xn1.negate());
	    BigDecimal currentSquare = xn1.pow(2);
	    BigDecimal currentPrecision = currentSquare.subtract(c);
	    currentPrecision = currentPrecision.abs();
	    if (currentPrecision.compareTo(precision) <= -1){
	        return xn1;
	    }
	    return sqrtNewtonRaphson(c, xn1, precision);
	}

	/**
	 * Uses Newton Raphson to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	public static BigDecimal bigSqrt(BigDecimal c){
	    return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE));
	}

}
