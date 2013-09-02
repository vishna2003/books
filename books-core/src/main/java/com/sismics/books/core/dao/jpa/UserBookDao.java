package com.sismics.books.core.dao.jpa;

import java.util.UUID;

import javax.persistence.EntityManager;

import com.sismics.books.core.model.jpa.UserBook;
import com.sismics.util.context.ThreadLocalContext;


/**
 * User book DAO.
 * 
 * @author bgamard
 */
public class UserBookDao {
    /**
     * Creates a new user book.
     * 
     * @param userBook UserBook
     * @return New ID
     * @throws Exception
     */
    public String create(UserBook userBook) {
        // Create the UUID
        userBook.setId(UUID.randomUUID().toString());
        
        // Create the user book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(userBook);
        
        return userBook.getId();
    }
}
