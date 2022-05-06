package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
           UserService userService = new UserServiceImpl();
           userService.createUsersTable();

           userService.saveUser("Александр", "Александрович", (byte) 25);
           userService.saveUser("Иван", "Иванович", (byte) 15);
           userService.saveUser("Борис", "Борисович", (byte) 35);
           userService.saveUser("Саша", "Сашович", (byte) 14);

           System.out.println("\n");

           userService.getAllUsers().forEach(System.out::println);
           userService.cleanUsersTable();
           userService.dropUsersTable();




    }
}
