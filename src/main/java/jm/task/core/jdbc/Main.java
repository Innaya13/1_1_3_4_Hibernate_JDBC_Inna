package jm.task.core.jdbc;

import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Marina", "Petrova", (byte) 35));
        users.add(new User("Irina", "Petrova", (byte) 36));
        users.add(new User("Anna", "Petrova", (byte) 37));
        users.add(new User("Ivan", "Petrov", (byte) 38));

        for (User us : users) {
             userService.saveUser(us.getName(), us.getLastName(), (byte) us.getAge());
             System.out.println("User с именем – " + us.getName() + " добавлен в базу данных");
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
