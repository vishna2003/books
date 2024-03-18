package com.sismics.books.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.sismics.books.core.model.jpa.CommonLibraryBook;
import com.sismics.util.context.ThreadLocalContext;
import java.util.List;

/**
 * DAO class for CommonLibraryBook.
 */
public class CommonLibraryBookDao {

    /**
     * Creates a new CommonLibraryBook.
     * 
     * @param book CommonLibraryBook
     * @return New ID
     */
    public String create(CommonLibraryBook book) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(book);
        return book.getId();
    }
    
    /**
     * Gets a CommonLibraryBook by its ID.
     * 
     * @param id CommonLibraryBook ID
     * @return CommonLibraryBook
     */
    public CommonLibraryBook getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(CommonLibraryBook.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Finds books by title.
     * 
     * @param title Title of the book
     * @return List of CommonLibraryBook
     */
    public List<CommonLibraryBook> findByTitle(String title) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("SELECT b FROM CommonLibraryBook b WHERE b.title LIKE :title", CommonLibraryBook.class);
        q.setParameter("title", "%" + title + "%");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Updates the rating of a CommonLibraryBook.
     * 
     * @param id ID of the CommonLibraryBook
     * @param rating New rating to be set
     */
    public void updateRating(String id, Double rating) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        CommonLibraryBook book = em.find(CommonLibraryBook.class, id);
        if (book != null) {
            book.setRating(rating);
            em.merge(book);
        }
    }

    // Additional methods for handling CommonLibraryBook entities can be added here
}