package by.training.DBChecker.utils;
/**
 * Class data holder for info about row.
 */
public class DBRow {
	
	private String id = "";
	private String login = "";
	private String password = "";
	private String email = "";
	private String name = "";
	private String remember = "";
	
	public final String getId() {
		return id;
	}

	public final String getLogin() {
		return login;
	}

	public final String getPassword() {
		return password;
	}

	public final String getEmail() {
		return email;
	}

	public final String getName() {
		return name;
	}

	public final String getRemember() {
		return remember;
	}
	
	public final void setId(String id) {
		this.id = id;
	}

	public final void setLogin(String login) {
		this.login = login;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setRemember(String remember) {
		this.remember = remember;
	}
	
}
