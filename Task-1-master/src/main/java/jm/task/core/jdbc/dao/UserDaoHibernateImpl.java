package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = """
                create table if not exists users (
                id serial primary key, 
                name varchar(100), 
                lastname varchar(100),
                age smallint) 
                """;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = """
                drop table if exists users;
                """;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge((byte) age);

        // Получаем сессию и начинаем транзакцию
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Сохраняем пользователя в базе данных
            session.save(user);

            // Подтверждаем транзакцию
            transaction.commit();

            System.out.println(name + " успешно добавлен в таблицу.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

}



    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Находим пользователя по ID
            User user = (User) session.get(User.class, id);

            if (user != null) {
                // Если пользователь найден, удаляем его
                session.delete(user);
                System.out.println("Пользователь с ID " + id + " успешно удалён.");
            } else {
                System.out.println("Пользователь с ID " + id + " не найден.");
            }

            // Подтверждаем транзакцию
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откат в случае ошибки
            }
            e.printStackTrace();
        }


    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Создаем HQL запрос для получения всех пользователей
            Query<User> query = session.createQuery("FROM User", User.class);

            // Получаем список пользователей
            users = query.list();

            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откат при ошибке
            }
            e.printStackTrace();
        }

        return users; // Возвращаем список пользователей

}

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Выполняем HQL запрос для удаления всех пользователей
            session.createQuery("DELETE FROM User").executeUpdate();

            transaction.commit(); // Подтверждаем транзакцию
            System.out.println("Таблица пользователей очищена.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откат при ошибке
            }
            e.printStackTrace();
        }

    }
}

