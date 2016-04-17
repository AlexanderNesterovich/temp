package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phone {

	// Данные телефона
	private String phoneId = "";
	private String ownerId = "";
	private String number = "";

	// Конструктор для создания списка
	public Phone(String phoneId, String ownerId, String number) {
		this.phoneId = phoneId;
		this.ownerId = ownerId;
		this.number = number;
	}

	// Конструктор для добавления в базу
	public Phone(String ownerId, String number) {
		this.phoneId = "0";
		this.ownerId = ownerId;
		this.number = number;
	}

	public Phone() {
		// TODO Auto-generated constructor stub
	}

	public String getNumber() {
		return number;
	}

	public String getId() {
		return phoneId;
	}

	@Override
	public String toString() {
		return "Phone [phoneId=" + phoneId + ", ownerId=" + ownerId + ", number=" + number + "]";
	}

	public void setId(String id) {
		this.phoneId = id;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOwnerId() {
		return ownerId;
	}

	// Валидация телефонного номера
	public boolean validateNumber(String number) {
		Matcher matcher = Pattern.compile("^[0-9+#-_]{2,50}$").matcher(number);
		return matcher.matches();
	}

}
