package com.sismics.books.core.dao.jpa;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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

    /**
     * Return a user book.
     * 
     * @param userBookId User book ID
     * @param userId User ID
     * @return User book
     */
    public UserBook getUserBook(String userBookId, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ub from UserBook ub where ub.id = :userBookId and ub.userId = :userId and ub.deleteDate is null");
        q.setParameter("userBookId", userBookId);
        q.setParameter("userId", userId);
        try {
            return (UserBook) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Return a user book
     * @param userBookId User book ID
     * @return User book
     */
    public UserBook getUserBook(String userBookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ub from UserBook ub where ub.id = :userBookId and ub.deleteDate is null");
        q.setParameter("userBookId", userBookId);
        try {
            return (UserBook) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
