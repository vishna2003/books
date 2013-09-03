package com.sismics.books.core.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import com.sismics.books.core.model.jpa.Book;

/**
 * Book utilities.
 * TODO Use http://openlibrary.org/api/volumes/brief/json/isbn:0345376595 to fallback
 * 
 * @author bgamard
 */
public class BookUtil {

    /**
     * Google Books API Search URL.
     */
    // TODO Parametrize API key (T_CONFIG table and AppResource to modify it, warning if no key at all)
    public static final String GOOGLE_BOOKS_SEARCH_FORMAT = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s&key=AIzaSyC8CXDtLo-3ErzRF5rXTcAnLbtGjKLgUEE";
    
    /**
     * Parser for multiple date formats;
     */
    private static DateTimeFormatter formatter;
    
    static {
        // Initialize date parser
        DateTimeParser[] parsers = { 
                DateTimeFormat.forPattern("yyyy").getParser(),
                DateTimeFormat.forPattern("yyyy-MM").getParser(),
                DateTimeFormat.forPattern("yyyy-MM-dd").getParser() };
        formatter = new DateTimeFormatterBuilder().append( null, parsers ).toFormatter();
    }
    
    /**
     * Search a book by its ISBN.
     * 
     * @return Book found
     * @throws Exception
     */
    public static Book searchBook(String isbn) throws Exception {
        // Sanitize ISBN (keep only digits)
        isbn = isbn.replaceAll("[^\\d]", "");
        
        URL url = new URL(String.format(Locale.ENGLISH, GOOGLE_BOOKS_SEARCH_FORMAT, isbn));
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.62 Safari/537.36");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        InputStream inputStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(inputStream, JsonNode.class);
        ArrayNode items = (ArrayNode) rootNode.get("items");
        if (rootNode.get("totalItems").getIntValue() <= 0) {
            throw new Exception("No book found for ISBN: " + isbn);
        }
        JsonNode item = items.get(0);
        JsonNode volumeInfo = item.get("volumeInfo");
        
        // Build the book
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setTitle(volumeInfo.get("title").getTextValue());
        book.setSubtitle(volumeInfo.has("subtitle") ? volumeInfo.get("subtitle").getTextValue() : null);
        ArrayNode authors = (ArrayNode) volumeInfo.get("authors");
        if (authors.size() <= 0) {
            throw new Exception("Author not found");
        }
        book.setAuthor(authors.get(0).getTextValue());
        book.setDescription(volumeInfo.has("description") ? volumeInfo.get("description").getTextValue() : null);
        ArrayNode industryIdentifiers = (ArrayNode) volumeInfo.get("industryIdentifiers");
        Iterator<JsonNode> iterator = industryIdentifiers.getElements();
        while (iterator.hasNext()) {
            JsonNode industryIdentifier = iterator.next();
            if ("ISBN_10".equals(industryIdentifier.get("type").getTextValue())) {
                book.setIsbn10(industryIdentifier.get("identifier").getTextValue());
            } else if ("ISBN_13".equals(industryIdentifier.get("type").getTextValue())) {
                book.setIsbn13(industryIdentifier.get("identifier").getTextValue());
            }
        }
        book.setLanguage(volumeInfo.get("language").getTextValue());
        book.setPageCount(volumeInfo.has("pageCount") ? volumeInfo.get("pageCount").getLongValue() : null);
        book.setPublishDate(formatter.parseDateTime(volumeInfo.get("publishedDate").getTextValue()).toDate());
        
        Thread.sleep(100); // TODO Throttle API calls with a service
        
        // Download the thumbnail
        JsonNode imageLinks = volumeInfo.get("imageLinks");
        if (imageLinks != null && imageLinks.has("thumbnail")) {
            String imageUrl = imageLinks.get("thumbnail").getTextValue();
            URLConnection imageConnection = new URL(imageUrl).openConnection();
            imageConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.62 Safari/537.36");
            imageConnection.setConnectTimeout(10000);
            imageConnection.setReadTimeout(10000);
            InputStream imageInputStream = imageConnection.getInputStream();
            Path imagePath = Paths.get(DirectoryUtil.getBookDirectory().getPath(), book.getId());
            Files.copy(imageInputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            
            Thread.sleep(100); // TODO Throttle API calls with a service
        }
        
        return book;
    }
}
