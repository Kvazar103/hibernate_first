import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build();

        Metadata metadata =
                new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(User.class) /*!!!!!!! register class*/
                        .getMetadataBuilder()
                        .build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(new User("vasya","pupkin"));
        session.save(new User("petro","petrovich"));
        session.save(new User("object","third"));

        session.getTransaction().commit();

        List<User> users=session.createNativeQuery("select * from User",User.class).list();//SQL
        System.out.println(users);

        List<User> users1=session.createQuery("select u from User u",User.class).list();//HQL
        System.out.println(users1);

        User user = session.find(User.class, 2);
        System.out.println(user);

        session.close();
        sessionFactory.close();
    }
}