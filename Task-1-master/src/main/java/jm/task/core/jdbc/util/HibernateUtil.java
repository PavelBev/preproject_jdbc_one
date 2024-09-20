package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        try {
            // Создаем конфигурацию Hibernate
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "75351595");
            configuration.setProperty("hibernate.hbm2ddl.auto", "create");
            configuration.setProperty("show_sql", "true");

            // Добавляем классы сущностей
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Обработка ошибок конфигурации
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        // Закрываем кэшные соединения
        getSessionFactory().close();
    }
}