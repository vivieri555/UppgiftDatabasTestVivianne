package com.eriksson.util;

import com.eriksson.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public final class HibernateUtil {

    private static final String PROPERTIES_FILE = "hibernate.properties";

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {}

public static SessionFactory getSessionFactory(){ return SESSION_FACTORY; }

public static void shutdown() {
    if (!SESSION_FACTORY.isClosed()) {
        SESSION_FACTORY.close();
    }
}

        private static SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            try (InputStream in =
                         HibernateUtil.class
                                 .getClassLoader()
                                 .getResourceAsStream(PROPERTIES_FILE)) {
                if (in == null) {
                    throw new IllegalStateException(
                            PROPERTIES_FILE + " not found in resources"
                    );
                }
                properties.load(in);
            }

            Configuration cfg = new Configuration();
            cfg.setProperties(properties);

            cfg.addAnnotatedClass(Bike.class);
            cfg.addAnnotatedClass(Car.class);
            cfg.addAnnotatedClass(Caravan.class);
            cfg.addAnnotatedClass(Member.class);
            cfg.addAnnotatedClass(Rental.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties())
                            .build();

            return cfg.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to build SessionFactory " + e.getMessage());
        }
    }
}
