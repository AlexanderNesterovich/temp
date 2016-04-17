package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.DBWorker;

public class Person {
	// Извлечение телефонов человека из БД.
	// Данные записи о человеке.
	private DBWorker db = DBWorker.getInstance();
	private String id = "";
	private String name = "";
	private String surname = "";
	private String middlename = "";
	private Map<String, Phone> phones = new TreeMap<String, Phone>();

	// Конструктор для создания записи о человеке на основе данных из БД.
	public Person(String id, String name, String surname, String middlename) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;

		// Извлечение телефонов человека из БД.
		ResultSet db_data = DBWorker.getInstance().getDBData("SELECT * FROM `phone` WHERE `owner`=" + id);

		try {
			// Если у человека нет телефонов, ResultSet будет == null.
			if (db_data != null) {
				while (db_data.next()) {
					this.phones.put(db_data.getString("id"),
							new Phone(db_data.getString("id"), id, db_data.getString("number")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", middlename=" + middlename + "]";
	}

	// Конструктор для создания пустой записи о человеке.
	public Person() {
		this.id = "0";
		this.name = "";
		this.surname = "";
		this.middlename = "";
	}

	// Конструктор для создания записи, предназначенной для добавления в БД.
	public Person(String name, String surname, String middlename) {
		this.id = "0";
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;
	}

	// Валидация частей ФИО. Для отчества можно передать второй параетр == true,
	// тогда допускается пустое значение.
	public boolean validateFMLNamePart(String fml_name_part, boolean empty_allowed) {
		if (empty_allowed) {
			Matcher matcher = Pattern.compile("^[0-9\\p{L}_-]{0,150}$").matcher(fml_name_part);
			return matcher.matches();
		} else {
			Matcher matcher = Pattern.compile("^[0-9\\p{L}_-]{1,150}$").matcher(fml_name_part);
			return matcher.matches();
		}

	}
	//Удаление телефона
	public boolean deletePhone(String id) {
		if ((id != null) && (!id.equals("null"))) {
			int filtered_id = Integer.parseInt(id);

			Integer affected_rows = DBWorker.getInstance()
					.changeDBData("DELETE FROM `phone` WHERE `id`=" + filtered_id);

			// Если удаление прошло успешно...
			if (affected_rows > 0) {
				// Удаляем запись о человеке из общего списка.
				this.phones.remove(id);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// Добавление нового телефона
	public boolean addPhone(Phone new_phone) {

		String query = "";
		// Если номер не пустой
		if (!new_phone.getNumber().equals("")) {
			query = "INSERT INTO `phone` (`owner`, `number`) VALUES ('" + new_phone.getOwnerId() + "', '"
					+ new_phone.getNumber() + "')";
		}

		Integer affected_rows = this.db.changeDBData(query);

		// Если добавление прошло успешно...
		if (affected_rows > 0) {

			// Добавляем запись о телефоне в общий список.
			new_phone.setId(this.db.getLastInsertId().toString());
			this.phones.put(new_phone.getId(), new_phone);
			return true;
		} else {
			return false;
		}
	}

	// Обновляем телефон
	public boolean updatePhone(String id, Phone new_phone) {

		String query = "";

		// Проверяем на пустую строку
		if (!new_phone.getNumber().equals("")) {
			query = "UPDATE `phone` SET `number` = '" + new_phone.getNumber() + "' WHERE `id` = " + id;
		}

		Integer affected_rows = this.db.changeDBData(query);

		// Если обновление прошло успешно...
		if (affected_rows > 0) {
			return true;
		} else {
			return false;
		}
	}

	// ++++++++++++++++++++++++++++++++++++++
	// Геттеры и сеттеры
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getMiddlename() {
		if ((this.middlename != null) && (!this.middlename.equals("null"))) {
			return this.middlename;
		} else {
			return "";
		}
	}

	public Map<String, Phone> getPhones() {
		return phones;
	}

	public Phone getPhone(String phoneId) {
		return phones.get(phoneId);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

}
