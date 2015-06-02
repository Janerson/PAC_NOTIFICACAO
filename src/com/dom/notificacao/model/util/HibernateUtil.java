package com.dom.notificacao.model.util;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author DOM
 */
public class HibernateUtil {

    public static final ThreadLocal<Session> threadLocal = new ThreadLocal<>();
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {

            e.printStackTrace();
        }
    }


/*
    public static Connection getConnection() {
        Method c = null;
        try {
            if (c == null) {
                c = getSession().getClass().getMethod("connection");
            }
            return (Connection) ReflectionUtils.invokeMethod(c, getSession());
        }
        catch (NoSuchMethodException ex) {
            throw new IllegalStateException("Cannot find connection() method on Hibernate session", ex);
        }
    }
*/


    public static Session getSession() {
        //Session session = threadLocal.get();

        if(threadLocal.get() == null){
            threadLocal.set(sessionFactory.openSession());
        }
//        if (session == null || !session.isOpen()) {
//            session = sessionFactory.openSession();
//            threadLocal.set(session);
//        }
//        return session;
        return threadLocal.get();
    }

    public static void closeSession() throws HibernateException {

        Session session = (Session) threadLocal.get();

        threadLocal.set(null);

        if (session != null || session.isOpen()) {
            session.close();
        }
    }

}