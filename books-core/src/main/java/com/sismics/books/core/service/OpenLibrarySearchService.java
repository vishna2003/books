package com.sismics.books.core.service;

import com.google.common.util.concurrent.RateLimiter;

import com.sismics.books.core.model.jpa.Book;

import java.net.URL;
import java.net.URLConnection;

import java.io.InputStream;

import org.joda.time.format.DateTimeFormatter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import java.util.UUID;
import java.util.Locale;

import com.neovisionaries.i18n.LanguageCode;

import com.sismics.books.core.service.ThumbnailDownloader; 



public class OpenLibrarySearchService implements BookSearchService {
    private RateLimiter openLibraryRateLimiter = RateLimiter.create(0.33332);
    private static DateTimeFormatter formatter;
    private static final String OPEN_LIBRARY_FORMAT = "http://openlibrary.org/api/volumes/brief/isbn/%s.json";
    
    public OpenLibrarySearchService(DateTimeFormatter formatter){
            this.formatter = formatter; 
    }

    @Override
    public Book searchBook(String isbn) throws Exception {
        openLibraryRateLimiter.acquire();
        
        URL url = new URL(String.format(Locale.ENGLISH, OPEN_LIBRARY_FORMAT, isbn));
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.62 Safari/537.36");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        InputStream inputStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(inputStream, JsonNode.class);

        if (rootNode instanceof ArrayNode) {
            throw new Exception("No book found for ISBN: " + isbn);
        }        
        JsonNode bookNode = rootNode.get("records").getElements().next();
        JsonNode details = bookNode.get("details").get("details");
        JsonNode data = bookNode.get("data");
        
        // Build the book
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setTitle(details.get("title").getTextValue());
        book.setSubtitle(details.has("subtitle") ? details.get("subtitle").getTextValue() : null);
        if (!data.has("authors") || data.get("authors").size() == 0) {
            throw new Exception("Book without author for ISBN: " + isbn);
        }
        book.setAuthor(data.get("authors").get(0).get("name").getTextValue());
        book.setDescription(details.has("first_sentence") ? details.get("first_sentence").get("value").getTextValue() : null);
        book.setIsbn10(details.has("isbn_10") && details.get("isbn_10").size() > 0 ? details.get("isbn_10").get(0).getTextValue() : null);
        book.setIsbn13(details.has("isbn_13") && details.get("isbn_13").size() > 0 ? details.get("isbn_13").get(0).getTextValue() : null);
        if (details.has("languages") && details.get("languages").size() > 0) {
            String language = details.get("languages").get(0).get("key").getTextValue();
            LanguageCode languageCode = LanguageCode.getByCode(language.split("/")[2]);
            book.setLanguage(languageCode.name());
        }
        book.setPageCount(details.has("number_of_pages") ? details.get("number_of_pages").getLongValue() : null);
        if (!details.has("publish_date")) {
            throw new Exception("Book without publication date for ISBN: " + isbn);
        }
        book.setPublishDate(details.has("publish_date") ? formatter.parseDateTime(details.get("publish_date").getTextValue()).toDate() : null);
        
        // Download the thumbnail
        ThumbnailDownloader thumbnailDownloader = new ThumbnailDownloader(); 
        if (details.has("covers") && details.get("covers").size() > 0) {
            String imageUrl = "http://covers.openlibrary.org/b/id/" + details.get("covers").get(0).getLongValue() + "-M.jpg";
            thumbnailDownloader.downloadThumbnail(book, imageUrl);
        }
        
        return book;
    }
}