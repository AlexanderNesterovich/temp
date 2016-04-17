package by.training.booker;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	
	/*Напишите «небольшую бухгалтерскую программу», принимающую с клавиа-туры информацию о товарах 
	(тип, наименование, количество, стоимость одной еди-ницы). 
	При получении некоторой команды (реализуйте на своё усмотрение) про-грамма должна выдать следующую информацию:
		• количество типов товаров;
		• общее количество товаров;
		• средняя стоимость товара;
		• средняя стоимость товара каждого типа.*/
	
	static List<Product> productList = new LinkedList<>();
	static Inputter inputter = new Inputter();

	public static void main(String[] args) {
		menu();
		inputter.close();
	}
	
	public static void menu() {
		
		String choice = inputter.choice();
		
		if (choice.equals("1")) {
			
			/*Filling list with products*/
			do {
				Product tmp = new Product();
				tmp.setType(inputter.inputText("Enter type of product!"));
				tmp.setName(inputter.inputText("Enter name of product!"));
				tmp.setAmount(inputter.inputAmount());
				tmp.setPrice(inputter.inputPrice());
				productList.add(tmp);
			}while(!inputter.stop());

			
			/*Print info about list content*/
		}else if(choice.equals("2")) {
			
			if(productList.size() > 0) {
				for(Product p: productList) {
					p.print();
				}
				
			}else{
				System.err.println("List is empty!");
			}

			
			/*Calculating for complex info*/
		}else if(choice.equals("3")) {
			
			if(productList.size() > 0) {

				Set<String> types = new TreeSet<>();
				BigInteger sumAmount = BigInteger.ZERO;
				BigDecimal sumPrice = BigDecimal.ZERO;
				for(Product p: productList) {
					types.add(p.getType());
					sumAmount = sumAmount.add(p.getAmount());
					sumPrice = sumPrice.add(p.getPrice().multiply(new BigDecimal(p.getAmount())));
				}
				
				System.out.println("Amount of types = " + types.size());
				System.out.println("Amount of products = " + sumAmount.toString());
				System.out.println("Average price of product = " + sumPrice.divide(new BigDecimal(sumAmount), 3, RoundingMode.HALF_UP));
				
				for(String type: types) {
					
					sumAmount = BigInteger.ZERO;
					sumPrice = BigDecimal.ZERO;
					
					for(Product p: productList) {
						if(p.getType().equals(type)) {
							sumPrice = sumPrice.add(p.getPrice().multiply(new BigDecimal(p.getAmount())));
							sumAmount = sumAmount.add(p.getAmount());
						}
					}
					
					System.out.println("Average price of " + type + " = " + sumPrice.divide(new BigDecimal(sumAmount), 3, RoundingMode.HALF_UP));
					
				}
				
			}else{
				System.err.println("List is empty!");
			}
			
		}else if(choice.equals("4")) {
			inputter.close();
			System.exit(0);
			
		}
			menu();
	}

}
