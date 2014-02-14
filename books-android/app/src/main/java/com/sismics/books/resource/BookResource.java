package com.sismics.books.resource;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Access to /book API.
 * 
 * @author bgamard
 */
public class BookResource extends BaseResource {
    /**
     * List all books
     * @param context Context
     * @param responseHandler Response handler
     */
    public static void list(Context context, JsonHttpResponseHandler responseHandler) {
        init(context);

        client.get(getApiUrl(context) + "/book/list?asc=true&offset=0&limit=100&sort_column=3", responseHandler);
    }

    /**
     * Get the book data.
     * @param context Context
     * @param id Book ID
     * @param responseHandler Response handler
     */
    public static void info(Context context, String id, JsonHttpResponseHandler responseHandler) {
        init(context);

        client.get(getApiUrl(context) + "/book/" + id, responseHandler);
    }
}
