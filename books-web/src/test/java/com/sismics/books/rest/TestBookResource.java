package com.sismics.books.rest;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.sismics.books.rest.filter.CookieAuthenticationFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Test the book resource.
 * 
 * @author bgamard
 */
public class TestBookResource extends BaseJerseyTest {
    /**
     * Test the book resource.
     * 
     * @throws JSONException
     */
    @Test
    public void testBookResource() throws JSONException {
        // Login book1
        clientUtil.createUser("book1");
        String book1Token = clientUtil.login("book1");
        
        // Add a book
        WebResource bookResource = resource().path("/book");
        bookResource.addFilter(new CookieAuthenticationFilter(book1Token));
        MultivaluedMapImpl postParams = new MultivaluedMapImpl();
        postParams.add("isbn", "9780345376596");
        ClientResponse response = bookResource.put(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        JSONObject json = response.getEntity(JSONObject.class);
        String book1Id = json.optString("id");
        Assert.assertNotNull(book1Id);
    }
}