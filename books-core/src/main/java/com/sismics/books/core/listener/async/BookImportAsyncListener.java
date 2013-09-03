package com.sismics.books.core.listener.async;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Strings;
import com.google.common.eventbus.Subscribe;
import com.sismics.books.core.dao.jpa.BookDao;
import com.sismics.books.core.dao.jpa.UserBookDao;
import com.sismics.books.core.event.BookImportedEvent;
import com.sismics.books.core.model.jpa.Book;
import com.sismics.books.core.model.jpa.UserBook;
import com.sismics.books.core.util.BookUtil;
import com.sismics.books.core.util.TransactionUtil;

/**
 * Listener on books import request.
 * 
 * @author bgamard
 */
public class BookImportAsyncListener {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(BookImportAsyncListener.class);

    /**
     * Process the event.
     * 
     * @param bookImportedEvent Book imported event
     * @throws Exception
     */
    @Subscribe
    public void on(final BookImportedEvent bookImportedEvent) throws Exception {
        if (log.isInfoEnabled()) {
            log.info(MessageFormat.format("Books import requested event: {0}", bookImportedEvent.toString()));
        }
        
        // Create books
        TransactionUtil.handle(new Runnable() {
            @Override
            public void run() {
                CSVReader reader = null;
                BookDao bookDao = new BookDao();
                UserBookDao userBookDao = new UserBookDao();
                try {
                    reader = new CSVReader(new FileReader(bookImportedEvent.getImportFile()));
                } catch (FileNotFoundException e) {
                    log.error("Unable to read CSV file", e);
                }
                
                // Headers:
                // Book Id,Title,Author,Author l-f,Additional Authors,ISBN,ISBN13,My Rating,Average Rating,Publisher,Binding,Number of Pages,Year Published,Original Publication Year,Date Read,Date Added,Bookshelves,Bookshelves with positions,Exclusive Shelf,My Review,Spoiler,Private Notes,Read Count,Recommended For,Recommended By,Owned Copies,Original Purchase Date,Original Purchase Location,Condition,Condition Description,BCID
                String [] line;
                try {
                    while ((line = reader.readNext()) != null) {
                        if (line[0].equals("Book Id")) {
                            // Skip header
                            continue;
                        }
                        
                        // Retrieve ISBN number
                        String isbn = Strings.isNullOrEmpty(line[5]) ? line[6] : line[5];
                        if (Strings.isNullOrEmpty(isbn)) {
                            log.warn("No ISBN number for Goodreads book ID: " + line[0]);
                            continue;
                        }
                        
                        // Fetch the book from database if it exists
                        Book book = bookDao.getByIsbn(isbn);
                        if (book == null) {
                            // Try to get the book from a public API
                            try {
                                book = BookUtil.searchBook(isbn);
                            } catch (Exception e) {
                                log.error("Unable to find the book", e);
                                continue;
                            }
                            
                            // Save the new book in database
                            bookDao.create(book);
                        }
                        
                        // Create a new user book if needed
                        UserBook userBook = userBookDao.getByBook(book.getId(), bookImportedEvent.getUser().getId());
                        if (userBook == null) {
                            userBook = new UserBook();
                            userBook.setUserId(bookImportedEvent.getUser().getId());
                            userBook.setBookId(book.getId());
                            userBook.setCreateDate(new Date());
                            userBookDao.create(userBook);
                            
                            // TODO Import read date and creation date
                            // TODO Import bookshelves in tags (and create them if necessary)
                        }
                    }
                } catch (Exception e) {
                    log.error("Error parsing CSV line", e);
                }
            }
        });
    }
}
