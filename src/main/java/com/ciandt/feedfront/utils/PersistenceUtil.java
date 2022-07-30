package com.ciandt.feedfront.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("feedfront");

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
