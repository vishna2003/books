package com.sismics.books.rest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.rest.filter.CookieAuthenticationFilter;
import com.sismics.books.rest.model.FacebookUser;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Test the connect resource.
 * 
 * @author jtremeaux
 */
public class TestConnectResource extends BaseJerseyTest {
    private final static FacebookUser carol = new FacebookUser("100006592720905",
            "zhukajl_carrierostein_1378902582@tfbnw.net",
            "Carol Amfeibgbjije Carrierostein",
            "CAAFgAhxSoTsBAFMGpAZBgkqpu8XORKLaL4eoA2qjNLcRJJkA3ru2qGcrISuurWuTMgkjNFDH4K2orsoLmdMqurZBPB2VzSZA06h5T1I2UaN4XCrqQdFlwJF196tNZCK6cstzCIrpZAFArYetpJLF6TEyrsC1zoNzz1dVKUcYzblUHh7mZC9e49ieOGl58JSiylCQfZBOCHSAAZDZD");
    
    private final static FacebookUser charlie = new FacebookUser("100006740402697",
            "gpowenc_letuchyberg_1378902581@tfbnw.net",
            "Charlie Amfgdjdjbfig Letuchyberg",
            "CAAFgAhxSoTsBAJZCjuZAg1ZBdAZB1Y54SXVRR64wDYIHcU7Cpb2mabPWZCnZCfNx377fGwvay4OrpUmXnJ9PjZCCL5p09ctLHnjPz3iOFo7KTNTkJPFaRx9dK1zXaHF04JxnIgxlOwzm0ga6wzLiSxLj51D23IZCRlzXzVPVOscsUXUdrqd5aMUzRYZCcuZAECmy10XaU4DfAAqQZDZD");
    
    private final static FacebookUser bob = new FacebookUser("100006660036207",
            "iwipwxz_warmanberg_1378902579@tfbnw.net",
            "Bill Amfffkcfbjg Warmanberg",
            "CAAFgAhxSoTsBAK1aqCN3nzt5SoD3E73ktDvrcsCrcodbefbwjxUmCtvswQxXkL2PH2DqQcVLJpkQrI9ezZCLwAheX14EsSoLgwOna6ZBxYy2skyREngmb1KerFxHT6rH6BomCODwkaKaTjIZC7TnqcMlLvaE15WcMYbdLSNXwwPrhu4WoUzHUeyl4NIM0raAkRrF0CXJQZDZD");
    
    /**
     * Test of connected application list.
     * 
     * @throws JSONException
     */
    @Test
    public void testListApp() throws JSONException {
        // Create and connect user connect_list
        clientUtil.createUser("connect_list");
        String connectListAuthToken = clientUtil.login("connect_list");
        
        // List applications
        WebResource listResource = resource().path("/connect/list");
        listResource.addFilter(new CookieAuthenticationFilter(connectListAuthToken));
        ClientResponse response = listResource.get(ClientResponse.class);
        response = listResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        JSONObject json = response.getEntity(JSONObject.class);
        JSONArray apps = json.getJSONArray("apps");
        Assert.assertTrue(apps.length() > 0);
    }

    /**
     * Test of Facebook connection.
     * 
     * @throws JSONException
     */
    @Test
    public void testFacebook() throws JSONException {
        // Create and connect user carol_fb
        clientUtil.createUser("carol_fb");
        String carolFbAuthToken = clientUtil.login("carol_fb");
        
        // Create and connect user charlie_fb
        clientUtil.createUser("charlie_fb");
        String charlieFbAuthToken = clientUtil.login("charlie_fb");
        
        // Create and connect user bob_fb
        clientUtil.createUser("bob_fb");
        String bobFbAuthToken = clientUtil.login("bob_fb");
        
        // Add Myspace application : KO (application unknown)
        WebResource connectResource = resource().path("/connect/myspace/add");
        connectResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        MultivaluedMapImpl postParams = new MultivaluedMapImpl();
        postParams.putSingle("access_token", "123456789");
        ClientResponse response = connectResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
        JSONObject json = response.getEntity(JSONObject.class);
        Assert.assertEquals("AppNotFound", json.getString("type"));

        // Carol lists its applications : Facebook application not connected
        WebResource listResource = resource().path("/connect/list");
        listResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        response = listResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        JSONArray apps = json.getJSONArray("apps");
        Assert.assertEquals(1, apps.length());
        JSONObject app = (JSONObject) apps.get(0);
        Assert.assertEquals("FACEBOOK", app.optString("id"));
        Assert.assertEquals(false, app.optBoolean("connected"));
        Assert.assertEquals(false, app.optBoolean("sharing"));

        // Carol add Facebook application
        connectResource = resource().path("/connect/facebook/add");
        connectResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        postParams = new MultivaluedMapImpl();
        postParams.putSingle("access_token", carol.accessToken);
        response = connectResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        AppContext.getInstance().waitForAsync();

        // Carol lists its application : Facebook application connected
        listResource = resource().path("/connect/list");
        listResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        response = listResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        apps = json.getJSONArray("apps");
        Assert.assertEquals(3, apps.length());
        app = (JSONObject) apps.get(0);
        Assert.assertEquals("FACEBOOK", app.optString("id"));
        Assert.assertEquals(true, app.optBoolean("connected"));
        Assert.assertEquals(true, app.optBoolean("sharing"));

        // Carol lists its Facebook contacts 
        WebResource contactListResource = resource().path("/connect/facebook/contact/list");
        contactListResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        response = contactListResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        JSONArray contacts = json.getJSONArray("contacts");
        Assert.assertTrue(contacts.length() >= 1);
        JSONObject contact = (JSONObject) contacts.get(0);
        Assert.assertEquals(charlie.id, contact.optString("external_id"));
        Assert.assertEquals(charlie.fullName, contact.optString("full_name"));

        // Carol searches FB contacts : nothing 
        contactListResource = resource().path("/connect/facebook/contact/list");
        contactListResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        MultivaluedMapImpl getParams = new MultivaluedMapImpl();
        getParams.putSingle("query", "not_my_friend");
        response = contactListResource.queryParams(getParams).get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        contacts = json.getJSONArray("contacts");
        Assert.assertEquals(0, contacts.length());

        // Carol searches FB contacts
        contactListResource = resource().path("/connect/facebook/contact/list");
        contactListResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        getParams = new MultivaluedMapImpl();
        getParams.putSingle("query", charlie.fullName);
        response = contactListResource.queryParams(getParams).get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        contacts = json.getJSONArray("contacts");
        Assert.assertEquals(1, contacts.length());

        // Carol disable Facebook sharing
        connectResource = resource().path("/connect/facebook/update");
        connectResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        postParams = new MultivaluedMapImpl();
        response = connectResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));

        // Carol liste ses applications : l'activité n'est pas partagée sur l'application Facebook
        listResource = resource().path("/connect/list");
        listResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        response = listResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        apps = json.getJSONArray("apps");
        Assert.assertEquals(3, apps.length());
        app = (JSONObject) apps.get(0);
        Assert.assertEquals("FACEBOOK", app.optString("id"));
        Assert.assertEquals(true, app.optBoolean("connected"));
        Assert.assertEquals(false, app.optBoolean("sharing"));

        // Charlie ajoute l'application Facebook
        connectResource = resource().path("/connect/facebook/add");
        connectResource.addFilter(new CookieAuthenticationFilter(charlieFbAuthToken));
        postParams = new MultivaluedMapImpl();
        postParams.putSingle("access_token", charlie.accessToken);
        response = connectResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        AppContext.getInstance().waitForAsync();

        // Bob ajoute l'application Facebook
        connectResource = resource().path("/connect/facebook/add");
        connectResource.addFilter(new CookieAuthenticationFilter(bobFbAuthToken));
        postParams = new MultivaluedMapImpl();
        postParams.putSingle("access_token", bob.accessToken);
        response = connectResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        AppContext.getInstance().waitForAsync();

        // Carol supprime sa connexion à Facebook
        connectResource = resource().path("/connect/facebook/remove");
        connectResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        postParams = new MultivaluedMapImpl();
        response = connectResource.post(ClientResponse.class, postParams);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));

        // Carol liste ses applications : application Facebook non connectée
        listResource = resource().path("/connect/list");
        listResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        response = listResource.get(ClientResponse.class);
        Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        apps = json.getJSONArray("apps");
        Assert.assertEquals(3, apps.length());
        app = (JSONObject) apps.get(0);
        Assert.assertEquals("FACEBOOK", app.optString("id"));
        Assert.assertEquals(false, app.optBoolean("connected"));
        Assert.assertEquals(false, app.optBoolean("sharing"));

        // Carol recherche un contact parmi ses contacts FB : KO (pas connecté) 
        contactListResource = resource().path("/connect/facebook/contact/list");
        contactListResource.addFilter(new CookieAuthenticationFilter(carolFbAuthToken));
        response = contactListResource.get(ClientResponse.class);
        Assert.assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
        json = response.getEntity(JSONObject.class);
        Assert.assertEquals("AppNotConnected", json.getString("type"));
    }
}