package jm.task.core.jdbc;
/*
 1. Создание таблицы User(ов)
 2. Добавление 4 User(ов) в таблицу с данными на свой выбор.
 После каждого добавления должен быть вывод в консоль
 ( User с именем – name добавлен в базу данных )
 3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 4. Очистка таблицы User(ов)
 5. Удаление таблицы
 */


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl daoJDBC = new UserServiceImpl();
        daoJDBC.createUsersTable();
        daoJDBC.saveUser("Pavel", "Biksaliev", (byte) 25);
        daoJDBC.saveUser("Rustam", "Biksaliev", (byte) 15);
        daoJDBC.saveUser("Kirill", "Gaincev", (byte) 39);
        daoJDBC.saveUser("Anatoly", "Kiselev", (byte) 33);
        daoJDBC.getAllUsers();
        daoJDBC.cleanUsersTable();
        daoJDBC.dropUsersTable();
//        daoJDBC.removeUserById(2);
    }
}