package net.mgorecki.translateandlearn.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class BingTranslationProvider implements TranslationProvider {

	private static String readInputStreamAsString(InputStream in) 
		    throws IOException {

		    BufferedInputStream bis = new BufferedInputStream(in);
		    ByteArrayOutputStream buf = new ByteArrayOutputStream();
		    int result = bis.read();
		    while(result != -1) {
		      byte b = (byte)result;
		      buf.write(b);
		      result = bis.read();
		    }        
		    return buf.toString();
		}
	
	public static String getAPIKey() throws TranslationException{
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("https://datamarket.accesscontrol.windows.net/v2/OAuth2-13");

	    String translated ="";
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("client_id", "TranslateAndLearnMgoreckiNet"));
	        nameValuePairs.add(new BasicNameValuePair("client_secret", "N98RnTMlWsrhTZ69RJ67D4fbFvFAIeGapcUA9ZGB0fY="));
	        nameValuePairs.add(new BasicNameValuePair("scope", "http://api.microsofttranslator.com"	));
	        nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);

	        translated=readInputStreamAsString(response.getEntity().getContent());
	        
	        
	    } catch (ClientProtocolException e) {
	        throw new TranslationException(e);
	    } catch (IOException e) {
	    	throw new TranslationException(e);
	    }
	    
	    return translated;
	}
	
	@Override
	public String translate(String text, String langFrom, String langTo) throws TranslationException {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost2 = new HttpPost("http://api.microsofttranslator.com/V2/Http.svc/GetTranslations");
	    HttpPost httppost = new HttpPost("http://api.microsofttranslator.com/v2/Http.svc/Detect");

	    String translated ="";
	    try {
	        // Add your data
	    	httppost.setHeader("Authorization", "Bearer http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier=TranslateAndLearnMgoreckiNet&http://schemas.microsoft.com/accesscontrolservice/2010/07/claims/identityprovider=https://datamarket.accesscontrol.windows.net/&Audience=http://api.microsofttranslator.com&ExpiresOn=1342745670&Issuer=https://datamarket.accesscontrol.windows.net/&HMACSHA256=MJVJrvJfPGnejJ7jdFHBhKY6vkGDFD12yYbuqGM5pe0=");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        //nameValuePairs.add(new BasicNameValuePair("appId", "http%3a%2f%2fschemas.xmlsoap.org%2fws%2f2005%2f05%2fidentity%2fclaims%2fnameidentifier=TranslateAndLearnMgoreckiNet&http%3a%2f%2fschemas.microsoft.com%2faccesscontrolservice%2f2010%2f07%2fclaims%2fidentityprovider=https%3a%2f%2fdatamarket.accesscontrol.windows.net%2f&Audience=http%3a%2f%2fapi.microsofttranslator.com&ExpiresOn=1342745670&Issuer=https%3a%2f%2fdatamarket.accesscontrol.windows.net%2f&HMACSHA256=MJVJrvJfPGnejJ7jdFHBhKY6vkGDFD12yYbuqGM5pe0%3d"));
	        nameValuePairs.add(new BasicNameValuePair("text", text));
	        nameValuePairs.add(new BasicNameValuePair("from", langFrom));
	        nameValuePairs.add(new BasicNameValuePair("to", langTo));
	        nameValuePairs.add(new BasicNameValuePair("maxTranslations", "255"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);

	        translated=readInputStreamAsString(response.getEntity().getContent());
	        
	        
	    } catch (ClientProtocolException e) {
	        throw new TranslationException(e);
	    } catch (IOException e) {
	    	throw new TranslationException(e);
	    }
	    
	    return translated;
	}

}
