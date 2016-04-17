package by.training.booker;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Product {

	private BigDecimal price = new BigDecimal(-1);
	private BigInteger amount = new BigInteger("-1", 10);
	private String name = "not initialized";
	private String type = "not initialized";
	
	public final BigDecimal getPrice() {
		return price;
	}
	public final void setPrice(BigDecimal price) {
		this.price = price;
	}
	public final BigInteger getAmount() {
		return amount;
	}
	public final void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getType() {
		return type;
	}
	public final void setType(String type) {
		this.type = type;
	}
	public void print() {
		System.out.println("Type = " + getType() + ". " +
				"Name = " + getName() + ". " +
				"Amount = " + getAmount().toString() + ". " +
				"Price = " + getPrice().toString() + ".");
		
	}
	
}
