package app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManagePersonServlet extends HttpServlet {

	// Идентификатор для сериализации/десериализации.
	private static final long serialVersionUID = 1L;

	// Основной объект, хранящий данные телефонной книги.
	private Phonebook phonebook;

	// Конструктор сервлета и первичная инициализация класса Phonebook.
	public ManagePersonServlet() {
		super();
		try {
			this.phonebook = Phonebook.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Валидация ФИО и генерация сообщения об ошибке в случае невалидных данных.
	private String validatePersonFMLName(Person person) {
		String error_message = "";

		if (!person.validateFMLNamePart(person.getName(), false)) {
			error_message += "Имя должно быть строкой от 1 до 150 символов из букв, цифр, знаков подчёркивания и знаков минус.<br />";
		}

		if (!person.validateFMLNamePart(person.getSurname(), false)) {
			error_message += "Фамилия должна быть строкой от 1 до 150 символов из букв, цифр, знаков подчёркивания и знаков минус.<br />";
		}

		if (!person.validateFMLNamePart(person.getMiddlename(), true)) {
			error_message += "Отчество должно быть строкой от 0 до 150 символов из букв, цифр, знаков подчёркивания и знаков минус.<br />";
		}

		return error_message;
	}

	// Валидация номера и генерация сообщения об ошибке в случае невалидных
	// данных.
	private String validatePersonNumber(Phone phone) {

		String error_message = "";
		if (!phone.validateNumber(phone.getNumber())) {
			error_message += "Телефон должен быть строкой от 2 до 50 символов из цифр, знаков подчёркивания, знаков минус и знаков решётки.";
		}
		return error_message;
	}

	// Реакция на GET-запросы.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Обязательно ДО обращения к любому параметру нужно переключиться в
		// UTF-8,
		// иначе русский язык при передаче GET/POST-параметрами превращается в
		// "кракозябры".
		request.setCharacterEncoding("UTF-8");
		// В JSP нам понадобится сама телефонная книга. Можно создать её
		// экземпляр там,
		// но с архитектурной точки зрения логичнее создать его в сервлете и
		// передать в JSP.
		request.setAttribute("phonebook", this.phonebook);

		// Хранилище параметров для передачи в JSP.
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();

		// Диспетчеры для передачи управления на разные JSP (разные
		// представления (view)).
		RequestDispatcher dispatcher_for_manager = request.getRequestDispatcher("WEB-INF/ManagePerson.jsp");
		RequestDispatcher dispatcher_for_list = request.getRequestDispatcher("WEB-INF/List.jsp");
		RequestDispatcher dispatcher_for_phones = request.getRequestDispatcher("WEB-INF/ManagePhone.jsp");

		// Действие (action) и идентификатор записи (id) над которой выполняется
		// это действие.
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String phoneId = request.getParameter("phoneId");
		HttpSession session = request.getSession(false);

		// Если идентификатор и действие не указаны, мы находимся в состоянии
		// "просто показать список и больше ничего не делать".
		if ((action == null) && (id == null)) {
			request.setAttribute("jsp_parameters", jsp_parameters);
			dispatcher_for_list.forward(request, response);
		}
		// Если же действие указано, то...
		else {
			switch (action) {
			// Добавление записи.
			case "add":
				// Создание новой пустой записи о пользователе.
				Person empty_person = new Person();

				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "add");
				jsp_parameters.put("next_action", "add_go");
				jsp_parameters.put("next_action_label", "Добавить");

				// Установка параметров JSP.
				request.setAttribute("person", empty_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_manager.forward(request, response);
				break;

			case "addPhone":
				// Получение пользователя которому будет принадлежать новый
				// номер.
				Person editable_person = this.phonebook.getPerson(id);
				// Создание новой пустой записи о телефоне.
				Phone new_phone = new Phone();

				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "add_phone");
				jsp_parameters.put("next_action", "add_phone_go");
				jsp_parameters.put("next_action_label", "Добавить номер");

				// Установка параметров JSP.
				request.setAttribute("person", editable_person);
				request.setAttribute("phone", new_phone);
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_phones.forward(request, response);
				break;

			// Редактирование записи.
			case "edit":

				// Извлечение из телефонной книги информации о редактируемой
				// записи.
				editable_person = this.phonebook.getPerson(id);

				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "edit");
				jsp_parameters.put("next_action", "edit_go");
				jsp_parameters.put("next_action_label", "Сохранить");

				// Установка параметров JSP.
				request.setAttribute("person", editable_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_manager.forward(request, response);
				break;

			// Удаление записи.
			case "delete":

				// Если запись удалось удалить...
				if (phonebook.deletePerson(id)) {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result_label", "Удаление выполнено успешно");
				}
				// Если запись не удалось удалить (например, такой записи
				// нет)...
				else {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result_label",
							"Ошибка удаления (возможно, запись не найдена)");
				}

				// Возвращаемся в корневой каталог List.
				response.sendRedirect("/");
				break;

			case "editPhone":
				// Извлечение из телефонной книги информации о редактируемой
				// записи и редактируемом телефоне.
				editable_person = this.phonebook.getPerson(id);
				Phone editable_phone = editable_person.getPhone(phoneId);

				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "edit_phone");
				jsp_parameters.put("next_action", "edit_phone_go");
				jsp_parameters.put("next_action_label", "Сохранить номер");

				// Установка параметров JSP.
				request.setAttribute("phone", editable_phone);
				request.setAttribute("person", editable_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_phones.forward(request, response);
				break;

			case "deletePhone":

				editable_person = this.phonebook.getPerson(id);
				// Если запись удалось удалить...
				if (editable_person.deletePhone(phoneId)) {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "DELETION_SUCCESS");
					session.setAttribute("current_action_result_label", "Удаление выполнено успешно");
				}
				// Если запись не удалось удалить (например, такой записи
				// нет)...
				else {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "DELETION_FAILURE");
					session.setAttribute("current_action_result_label",
							"Ошибка удаления (возможно, запись не найдена)");
				}
				// Возвращаемся к редактированию юзера.
				response.sendRedirect("/?action=edit&id=" + id);
				;
				break;
			}
		}
	}

	// Реакция на POST-запросы.
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Обязательно ДО обращения к любому параметру нужно переключиться в
		// UTF-8,
		// иначе русский язык при передаче GET/POST-параметрами превращается в
		// "кракозябры".
		request.setCharacterEncoding("UTF-8");

		// В JSP нам понадобится сама телефонная книга. Можно создать её
		// экземпляр там,
		// но с архитектурной точки зрения логичнее создать его в сервлете и
		// передать в JSP.
		request.setAttribute("phonebook", this.phonebook);

		// Хранилище параметров для передачи в JSP.
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();

		// Диспетчеры для передачи управления на разные JSP (разные
		// представления (view)).
		RequestDispatcher dispatcher_for_manager = request.getRequestDispatcher("WEB-INF/ManagePerson.jsp");
		RequestDispatcher dispatcher_for_phones = request.getRequestDispatcher("WEB-INF/ManagePhone.jsp");

		// Действие (add_go, edit_go) и идентификатор записи (id) над которой
		// выполняется это действие.
		String add_go = request.getParameter("add_go");
		String edit_go = request.getParameter("edit_go");
		String add_phone_go = request.getParameter("add_phone_go");
		String edit_phone_go = request.getParameter("edit_phone_go");
		String id = request.getParameter("id");
		String phoneId = request.getParameter("phoneId");
		HttpSession session = request.getSession(false);
		// Добавление записи.
		if (add_go != null) {
			// Создание записи на основе данных из формы.
			Person new_person = new Person(request.getParameter("name"), request.getParameter("surname"),
					request.getParameter("middlename"));

			// Валидация ФИО.
			String error_message = this.validatePersonFMLName(new_person);

			// Если данные верные, можно производить добавление.
			if (error_message.equals("")) {

				// Если запись удалось добавить...
				if (this.phonebook.addPerson(new_person)) {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "ADDITION_SUCCESS");
					session.setAttribute("current_action_result_label", "Добавление выполнено успешно");
				}
				// Если запись НЕ удалось добавить...
				else {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "ADDITION_FAILURE");
					session.setAttribute("current_action_result_label", "Ошибка добавления");
				}

				// Передача запроса в JSP.
				response.sendRedirect("/");
			}
			// Если в данных были ошибки, надо заново показать форму и сообщить
			// об ошибках.
			else {
				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "add");
				jsp_parameters.put("next_action", "add_go");
				jsp_parameters.put("next_action_label", "Добавить");
				jsp_parameters.put("current_action_result_label", error_message);
				// Установка параметров JSP.
				request.setAttribute("person", new_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_manager.forward(request, response);
			}
		}
		if (add_phone_go != null) {
			// Создание записи на основе данных из формы.
			Phone new_phone = new Phone(request.getParameter("id"), request.getParameter("number"));
			Person editable_person = phonebook.getPerson(id);
			// Валидация ФИО.
			String error_message = this.validatePersonNumber(new_phone);

			// Если данные верные, можно производить добавление.
			if (error_message.equals("")) {

				// Если запись удалось добавить...
				if (editable_person.addPhone(new_phone)) {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "ADDITION_SUCCESS");
					session.setAttribute("current_action_result_label", "Добавление выполнено успешно");
				}
				// Если запись НЕ удалось добавить...
				else {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "ADDITION_FAILURE");
					session.setAttribute("current_action_result_label", "Ошибка добавления");
				}

				// Установка параметров JSP.
				response.sendRedirect("/?action=edit&id=" + id);
			}
			// Если в данных были ошибки, надо заново показать форму и сообщить
			// об ошибках.
			else {
				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "add_phone");
				jsp_parameters.put("next_action", "add_phone_go");
				jsp_parameters.put("next_action_label", "Добавить номер");
				jsp_parameters.put("current_action_result_label", error_message);

				// Установка параметров JSP.
				request.setAttribute("person", this.phonebook.getPerson(id));
				request.setAttribute("jsp_parameters", jsp_parameters);
				// Передача запроса в JSP.
				dispatcher_for_phones.forward(request, response);
			}
		}

		if (edit_phone_go != null) {
			// Получение записи и её обновление на основе данных из формы.
			Person updatable_person = this.phonebook.getPerson(request.getParameter("id"));
			Phone updatable_phone = new Phone();
			updatable_phone.setNumber(request.getParameter("number"));

			// Валидация ФИО.
			String error_message = this.validatePersonNumber(updatable_phone);

			// Если данные верные, можно производить добавление.
			if (error_message.equals("")) {

				// Если запись удалось обновить...
				if (updatable_person.updatePhone(phoneId, updatable_phone)) {
					// Изменение данных в текущем листе.
					updatable_phone = updatable_person.getPhone(phoneId);
					updatable_phone.setNumber(request.getParameter("number"));
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "UPDATE_SUCCESS");
					session.setAttribute("current_action_result_label", "Обновление выполнено успешно");
				}
				// Если запись НЕ удалось обновить...
				else {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "UPDATE_FAILURE");
					session.setAttribute("current_action_result_label", "Ошибка обновления");
				}

				request.setAttribute("jsp_parameters", jsp_parameters);
				// Передача запроса в JSP.
				response.sendRedirect("/?action=edit&id=" + id);
			}
			// Если в данных были ошибки, надо заново показать форму и сообщить
			// об ошибках.
			else {

				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "edit_phone");
				jsp_parameters.put("next_action", "edit_phone_go");
				jsp_parameters.put("next_action_label", "Сохранить номер");
				jsp_parameters.put("current_action_result_label", error_message);

				// Установка параметров JSP.
				request.setAttribute("phone", updatable_person.getPhone(phoneId));
				request.setAttribute("person", updatable_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_phones.forward(request, response);

			}

		}
		// Редактирование записи.
		if (edit_go != null) {
			// Получение записи и её обновление на основе данных из формы.
			Person updatable_person = new Person();
			updatable_person.setName(request.getParameter("name"));
			updatable_person.setSurname(request.getParameter("surname"));
			updatable_person.setMiddlename(request.getParameter("middlename"));
			// Валидация ФИО.
			String error_message = this.validatePersonFMLName(updatable_person);

			// Если данные верные, можно производить добавление.
			if (error_message.equals("")) {
				// Если запись удалось обновить...
				if (this.phonebook.updatePerson(id, updatable_person)) {
					// Изменение данных в текущем листе.
					updatable_person = this.phonebook.getPerson(id);
					updatable_person.setName(request.getParameter("name"));
					updatable_person.setSurname(request.getParameter("surname"));
					updatable_person.setMiddlename(request.getParameter("middlename"));
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "UPDATE_SUCCESS");
					session.setAttribute("current_action_result_label", "Обновление выполнено успешно");
				}
				// Если запись НЕ удалось обновить...
				else {
					// Сохраняем сообщение в сессии.
					session.setAttribute("current_action_result", "UPDATE_FAILURE");
					session.setAttribute("current_action_result_label", "Ошибка обновления");
				}

				response.sendRedirect("/");
			}
			// Если в данных были ошибки, надо заново показать форму и сообщить
			// об ошибках.
			else {
				// Подготовка параметров для JSP.
				jsp_parameters.put("current_action", "edit");
				jsp_parameters.put("next_action", "edit_go");
				jsp_parameters.put("next_action_label", "Сохранить");
				jsp_parameters.put("current_action_result_label", error_message);

				// Установка параметров JSP.
				request.setAttribute("person", this.phonebook.getPerson(request.getParameter("id")));
				request.setAttribute("jsp_parameters", jsp_parameters);

				// Передача запроса в JSP.
				dispatcher_for_manager.forward(request, response);

			}
		}
	}
}
