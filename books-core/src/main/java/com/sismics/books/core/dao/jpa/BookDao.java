package com.sismics.books.core.dao.jpa;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.model.jpa.Book;
import com.sismics.util.context.ThreadLocalContext;



/**
 * Book DAO.
 * 
 * @author bgamard
 */
public class BookDao {
    /**
     * Creates a new book.
     * 
     * @param book Book
     * @return New ID
     * @throws Exception
     */
    public String create(Book book) {
        // Create the UUID
        book.setId(UUID.randomUUID().toString());
        
        // Create the book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(book);
        
        return book.getId();
    }

    /**
     * Returns a book by its ISBN number (10 or 13)
     * 
     * @param isbn ISBN Number (10 or 13)
     * @return Book
     */
    public Book getByIsbn(String isbn) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select b from Book b where b.isbn10 = :isbn or b.isbn13 = :isbn");
        q.setParameter("isbn", isbn);
        try {
            return (Book) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
