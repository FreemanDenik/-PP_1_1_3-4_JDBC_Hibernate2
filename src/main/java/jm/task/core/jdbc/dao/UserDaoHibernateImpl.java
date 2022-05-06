package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.util.List;
import java.util.Objects;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private Session session;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public void createUsersTable() {
        if (sessionFactory.isClosed()) {
            sessionFactory = Util.getSessionFactory();
            session = sessionFactory.openSession();
        }
    }

    @Override
    public void dropUsersTable() {
        if (sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.save(new User(name, lastName, age));
        System.out.printf("User с именем – \"%s\" добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {

        User user = session.load(User.class, id);
        session.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("FROM User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

}
