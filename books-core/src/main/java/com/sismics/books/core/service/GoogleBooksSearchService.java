package com.sismics.books.core.service;

import com.google.common.util.concurrent.RateLimiter;

import com.sismics.books.core.model.jpa.Book;

import java.net.URL;
import java.net.URLConnection;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import org.joda.time.format.DateTimeFormatter;

import java.util.UUID;
import java.util.Locale;
import java.util.Iterator;

import com.sismics.books.core.service.ThumbnailDownloader; 


public class GoogleBooksSearchService implements BookSearchService {
    private String apiKeyGoogle;
    private static DateTimeFormatter formatter;
    private RateLimiter googleRateLimiter = RateLimiter.create(20);
    private static final String GOOGLE_BOOKS_SEARCH_FORMAT = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s&key=%s";

    public GoogleBooksSearchService(String apiKeyGoogle, DateTimeFormatter formatter) {
        this.apiKeyGoogle = apiKeyGoogle;
        this.formatter = formatter; 
    }
    
    @Override
    public Book searchBook(String isbn) throws Exception {
        googleRateLimiter.acquire();
        
        URL url = new URL(String.format(Locale.ENGLISH, GOOGLE_BOOKS_SEARCH_FORMAT, isbn, apiKeyGoogle));
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
        

        // Download the thumbnail
        ThumbnailDownloader thumbnailDownloader = new ThumbnailDownloader(); 
        JsonNode imageLinks = volumeInfo.get("imageLinks");
        if (imageLinks != null && imageLinks.has("thumbnail")) {
            String imageUrl = imageLinks.get("thumbnail").getTextValue();
            thumbnailDownloader.downloadThumbnail(book, imageUrl);
        }
        
        return book;
    }
}