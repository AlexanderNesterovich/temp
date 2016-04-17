package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWorker {
	
	final private String DRIVER = "com.mysql.jdbc.Driver";
	final private String DB_LOGIN = "root";
	final private String DB_PASSWORD = "root";
	final private String conUrl = "jdbc:mysql://localhost/phonebook?user=" + DB_LOGIN + "&password=" + DB_PASSWORD + "&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
	
	// Количество рядов таблицы, затронутых последним запросом.
	private Integer affected_rows = 0;
	
	// Значение автоинкрементируемого первичного ключа, полученное после
	// добавления новой записи.
	private Integer last_insert_id = 0;

	// Указатель на экземпляр класса.
	private static DBWorker instance = null;
	
	// Метод для получения экземпляра класса (реализован Singleton).
	public static DBWorker getInstance()
	{
		if (instance == null)
		{
			instance = new DBWorker();
		}
	
		return instance;
	}
	
	// "Заглушка", чтобы экземпляр класса нельзя было получить напрямую.
	private DBWorker()
	{
	 // Просто "заглушка".			
	}
	
	// Выполнение запросов на выборку данных.
	public ResultSet getDBData(String query)
	{
		Statement statement;
		Connection connect;
		try
		{
			Class.forName(DRIVER).newInstance();
			connect = DriverManager.getConnection(conUrl);
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return resultSet;
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("null on getDBData()!");
		return null;

	}
	
	// Выполнение запросов на модификацию данных.
	public Integer changeDBData(String query)
	{
		Statement statement;
		Connection connect;
		try
		{
			Class.forName(DRIVER).newInstance();
			connect = DriverManager.getConnection(conUrl);
			statement = connect.createStatement();
			this.affected_rows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		
			// Получаем last_insert_id() для операции вставки.
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
            	this.last_insert_id = rs.getInt(1);
            }
			
			return this.affected_rows;
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("null on changeDBData()!");
		return null;
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++
	// Геттеры и сеттеры.
	public Integer getAffectedRowsCount()
	{
		return this.affected_rows;
	}
	
	public Integer getLastInsertId()
	{
		return this.last_insert_id;
	}
	// Геттеры и сеттеры.
	// -------------------------------------------------
}

