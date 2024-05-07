package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        String creatTable = "CREATE TABLE IF NOT EXISTS Users ( id INT AUTO_INCREMENT PRIMARY KEY, Username VARCHAR(50) NOT NULL, Lastname VARCHAR(50) NOT NULL, Age INT(3) NOT NULL )";

        try (Statement statement = connection.createStatement()) {
            statement.execute(creatTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLES IF EXISTS Users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        String addUser = "INSERT INTO Users (Username, Lastname, Age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addUser)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User " + name + " was successfully saved");
        } catch (SQLException r) {
            throw new RuntimeException(r);
        }
    }
    @Override
    public void removeUserById(long id) {
        String removeUser = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User " + id + " was successfully removed");
        } catch (SQLException r) {
            throw new RuntimeException(r);
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectAllUsers = "SELECT * FROM Users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("Username"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setAge(resultSet.getByte("Age"));
                users.add(user);
            }
        } catch (SQLException r) {
            throw new RuntimeException(r);
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        String cleanTable = "DELETE FROM Users";
        try (Statement statement = connection.prepareStatement(cleanTable)) {
            statement.executeUpdate(cleanTable);
        } catch (SQLException r) {
            throw new RuntimeException(r);
        }

    }
}
