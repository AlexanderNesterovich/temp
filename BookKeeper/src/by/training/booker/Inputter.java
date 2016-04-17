package by.training.booker;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Scanner;

public class Inputter {
	
	private Scanner scText = new Scanner(System.in);
	private Scanner scNumbers = new Scanner(System.in);
	
	public Inputter() {
		scNumbers.useLocale(Locale.US);
		scText.useLocale(Locale.US);
	}
	
	public String inputText(String s) {
		
		System.out.println(s);
		String tmp = scText.nextLine();
		while (tmp.isEmpty()) {
			System.err.println("Empty String. Try again!");
			System.out.println(s);
			tmp = scText.nextLine();
		}
		return tmp;
		
	}
	
	
	public String choice() {
		String tmp = inputText("Select command from list!\n1. Add product.\n2. Print product list.\n3. Print Info.\n4. Exit.");
		return tmp;
	}
	
	public BigInteger inputAmount() {
	
		System.out.println("Enter amount of the products");
		BigInteger tmp;
		while (true) {
			if (scNumbers.hasNextBigInteger()) {
				tmp = scNumbers.nextBigInteger();
				if (tmp.compareTo(BigInteger.ZERO) <= 0) {
					System.err.println("Please enter a positive integer number");
					continue;
				}else{
					break;
				}
			}else{
				System.err.println("Please enter a integer numeric value. For example: 500, 238000 etc");
				scNumbers.next();
				continue;
			}
		}
		return tmp;
	}
	
	public BigDecimal inputPrice() {
		
		System.out.println("Please enter your age");
		int tmp;
		while (true) {
			if (scNumbers.hasNextInt()) {
				tmp = scNumbers.nextInt();
				if (tmp <= 0 && tmp >= 150) {
					System.out.println("Please enter your age. Number from 1 to 150");
					continue;
				}else{
					break;
				}
			}else{
				System.out.println("Please enter your age. Number from 1 to 150");
				scNumbers.next();
				continue;
			}
		}
		return new BigDecimal(tmp);
	}
	
	
	public void close() {
		scText.close();
		scNumbers.close();
	}

	public boolean stop() {
		System.out.println("Do you want to stop? y for YES, anything else for NO");
		String tmp = scText.nextLine();
		if(tmp.equals("y")) {
			return true;
		}else{
			return false;
		}
	}

}
