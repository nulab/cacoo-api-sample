package com.cacoo.apisample.cacoo;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CacooUtils {
	// FIXME Replace with your Key and Secret. You can get them at https://cacoo.com/profile/apps.
	private static final String CONSUMER_KEY = "-- Consumer Key --";
	private static final String CONSUMER_SECRET = "-- Consumer Secret --";

	public static final String SESSION_CACOO_TOKEN = "SESSION_CACOO_TOKEN";
	public static final String SESSION_NEXT_URL = "SESSION_NEXT_URL";
	public static final String SESSION_OAUTH_PROVIDER = "SESSION_OAUTH_PROVIDER";
	public static final String SESSION_OAUTH_CONSUMER = "SESSION_OAUTH_CONSUMER";

	// Cacoo URL
	// FIXME If you have private enterprise package, change this URL to your system.
	public static final String CACOO_URL = "https://cacoo.com/";
	
	private static final String ACCESS_TOKEN_URL = CACOO_URL+"oauth/access_token";
	private static final String AUTHORIZE_URL = CACOO_URL+"oauth/authorize";
    private static final String REQUEST_TOKEN_URL = CACOO_URL+"oauth/request_token";
	
    /*
     * Access to Cacoo API.
     * If authentication is required in Cacoo, this will return null.
     * Redirect or forward has already been set.
     */
    public static HttpResponse cacooApi(HttpServletRequest req, HttpServletResponse resp, String apiPath, Map<String,String> params) 
			throws Exception{
    	// for HttpClient debugging
    	System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
    	System.setProperty("org.apache.commons.logging.simplelog.showdatetime","true");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http","DEBUG");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire","DEBUG");
    			
		OAuthConsumer consumer = createConsumer(req);
		if(consumer.getToken()==null){
			// User is not authenticated by OAuth.
			setupAuthRedirect(req, resp, consumer);
			return null;
		}
		// System.out.println("## Cacoo API : "+CACOO_URL+apiPath);
		HttpRequestBase method = null;
		if(params!=null && !params.isEmpty()){
			StringBuilder query = new StringBuilder();
			String sep = "";
			for(String name : params.keySet()){
				query.append(sep).append(name).append("=").append(URLEncoder.encode(params.get(name), "UTF-8"));
				sep="&";
			}
			// System.out.println("## POST "+query);
			HttpPost post = new HttpPost(CACOO_URL+apiPath);
			StringEntity entity = new StringEntity(query.toString());
			entity.setContentType("application/x-www-form-urlencoded");
			post.setEntity(entity);
			method = post;
		}else{
			method = new HttpGet(CACOO_URL+apiPath);
		}
		consumer.sign(method);
		HttpClient client = new DefaultHttpClient();
		HttpResponse res = client.execute(method);
		if(res.getStatusLine().getStatusCode()==401){
			/*
			 *  OAuth error.
			 *  The user has been authenticated previously, 
			 *  currently has not been authenticated.
			 *  401 is returned if the user has been deleted.
			 */
			// System.out.println("## status code 401");
			setupAuthRedirect(req, resp, consumer);
			return null;
		}
		return res;
	}
    
    /*
     * Convert the body part of the HTTP response to JSON.
     */
    public static JsonElement httpResponse2json(HttpResponse res){
    	try{
    		String s = EntityUtils.toString(res.getEntity());
    		// System.out.println("## JSON : "+s);
    		return new JsonParser().parse(s);
    	}catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /*
     * Retrieve OAuth access token.
     */
    public static OAuthToken retrieveAccessToken(HttpServletRequest req, String oauthVerifier) throws Exception{
    	HttpSession session = req.getSession();
    	OAuthConsumer consumer = (OAuthConsumer)session.getAttribute(SESSION_OAUTH_CONSUMER);
    	OAuthProvider provider = (OAuthProvider)session.getAttribute(SESSION_OAUTH_PROVIDER);
    	session.removeAttribute(SESSION_OAUTH_CONSUMER);
    	session.removeAttribute(SESSION_OAUTH_PROVIDER);
    	
		provider.retrieveAccessToken(consumer, oauthVerifier, new String[0]);
		OAuthToken token = new OAuthToken();
		token.setAccessToken(consumer.getToken());
		token.setTokenSecret(consumer.getTokenSecret());
		return token;
    }
    
    /*
     * URL for diagram editor.
     */
    public static String editorLink(String diagramId, String editorToken, String automationToken, HttpServletRequest req){
    	// FIXME Please specify the url to the "callbackURL" when users close editor page.
    	JsonObject root = new JsonObject();
    	root.addProperty("callbackUrl", createRequestHost(req)+req.getContextPath()+"/cacoo/");
    	JsonArray buttons = new JsonArray();
    	JsonObject closeButton = new JsonObject();
    	closeButton.addProperty("label", "CLOSE");
    	closeButton.addProperty("action", "saveAndExit");
    	buttons.add(closeButton);
    	root.add("buttons",buttons);
    	try{
    		String url = CACOO_URL+"diagrams/"+diagramId+"/edit?parameter="+URLEncoder.encode(root.toString(),"UTF-8");
    		if (editorToken != null && editorToken.length() > 0) {
    			url += "&editorToken="+URLEncoder.encode(editorToken, "UTF-8");
    		}
    		if (automationToken != null && automationToken.length() > 0) {
    			url += "&automationToken="+URLEncoder.encode(automationToken, "UTF-8");
    		}
    		return url;
    	}catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /*
     * Access Token and Token Secret for OAuth.
     */
    public static class OAuthToken{
    	private String accessToken;
    	private String tokenSecret;
		public String getAccessToken() {
			return accessToken;
		}
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
		public String getTokenSecret() {
			return tokenSecret;
		}
		public void setTokenSecret(String tokenSecret) {
			this.tokenSecret = tokenSecret;
		}
    	
    }
	
	/*
	 * Redirected to Cacoo for login.
	 */
	private static void setupAuthRedirect(HttpServletRequest req, HttpServletResponse resp, OAuthConsumer consumer) throws Exception{
		OAuthProvider provider = new CommonsHttpOAuthProvider(REQUEST_TOKEN_URL, ACCESS_TOKEN_URL, AUTHORIZE_URL);
		String query = req.getQueryString();
		if(query==null || query.isEmpty()){
			query = "";
		}else{
			query = "?"+query;
		}
		// Use in the CacooCallbackServlet.
		HttpSession session= req.getSession();
		session.setAttribute(SESSION_NEXT_URL, req.getRequestURL().toString()+query);
		session.setAttribute(SESSION_OAUTH_CONSUMER, consumer);
		session.setAttribute(SESSION_OAUTH_PROVIDER, provider);
		String url = null;
		try{
			url = provider.retrieveRequestToken(consumer, createCallbackURL(req), new String[0]);
			resp.sendRedirect(url);
		}catch (OAuthException e) {
			req.setAttribute("message", "Can't connect to Cacoo.");
			req
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(req, resp);
			return;
		}
	}
	
	/*
	 * Callback URL after OAuth authentication.
	 */
	private static String createCallbackURL(HttpServletRequest req){
		String url = createRequestHost(req);
		url += req.getContextPath()+"/cacoo/callback";
		return url;
	}
	
	/*
	 * Scheme and host part of the request URL.
	 * example: http://example.com:8080
	 */
	private static String createRequestHost(HttpServletRequest req){
		String url = req.getScheme()+"://"+req.getServerName();
		int port = req.getServerPort();
		if(port!=80){
			url += ":"+port;
		}
		return url;
	}
	
	/*
	 * Create OAuth consumer.
	 */
	private static OAuthConsumer createConsumer(HttpServletRequest req){
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
				CONSUMER_SECRET);
		// FIXME Usually the tokens will be retrieved from Database.
		HttpSession session = req.getSession();
		OAuthToken token = (OAuthToken)session.getAttribute(SESSION_CACOO_TOKEN);
		if(token!=null){
			consumer.setTokenWithSecret(token.getAccessToken(), token.getTokenSecret());
		}
		return consumer;
	}
    
}
