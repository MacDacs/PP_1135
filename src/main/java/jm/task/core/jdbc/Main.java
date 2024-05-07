package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Name1", "LastName1", (byte) 20);
        userService.saveUser("Name2", "LastName2", (byte) 25);
        userService.saveUser("Name3", "LastName3", (byte) 31);
        userService.saveUser("Name4", "LastName4", (byte) 38);

        List<User> allUsers = userService.getAllUsers();
        Stream<User> stream = allUsers.stream();
        stream.forEach(System.out::println);
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeConnection();


    }
}
