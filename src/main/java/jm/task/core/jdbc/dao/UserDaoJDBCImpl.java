package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = (Connection) Util.getSession();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                        "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(30), " +
                        "LASTNAME VARCHAR(30), AGE INT, PRIMARY KEY (ID) )");
        } catch (SQLException e) {
               e.printStackTrace();
           }
    }

    public void dropUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preStat = connection.prepareStatement(
                "INSERT INTO users (NAME, LASTNAME, AGE) VALUES(?, ?, ?)")) {

             preStat.setString(1, name);
             preStat.setString(2, lastName);
             preStat.setByte(3, age);

             preStat.executeUpdate();
             System.out.println("User was added!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preStat = connection.prepareStatement(
                "DELETE FROM users WHERE ID")) {

            preStat.setLong(1, id);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";

        try (Statement stat = connection.createStatement();
             ResultSet resultSet = stat.executeQuery(
                     "SELECT ID, NAME, LASTNAME, AGE FROM users")) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            }
        return userList;
    }
    public void cleanUsersTable() {
        try (Statement stat = connection.createStatement()) {
            stat.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
