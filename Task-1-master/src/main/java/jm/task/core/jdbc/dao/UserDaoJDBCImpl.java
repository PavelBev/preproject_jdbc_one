package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
//    PreparedStatement preparedStatement = null;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = """
                create table if not exists users (
                id serial primary key, 
                name varchar(100), 
                lastname varchar(100),
                age smallint) 
                """;

        try (Connection conn = Util.getConnection();
        Statement statement = conn.createStatement()) {
        statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = """
                drop table if exists users;
                """;

            try (Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()){
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                insert into users (name, lastname, age) values (?, ?, ?);
                """;

        try (Connection conn = Util.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            }


            System.out.println(name + " успешно добавлен в таблицу.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = """
                delete from users where id = ?;
                """;

        try (Connection conn = Util.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = """ 
                         select * from users
                     """;

        try(Connection conn = Util.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = """
                delete from users;
                """;
        try (Connection conn = Util.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            int rowsAffect = preparedStatement.executeUpdate();
            System.out.println("Удалено " + rowsAffect +  " записей");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
