package net.mgorecki.translateandlearn.service.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BingTranslationProvider implements TranslationProvider {

	public static int INFO_LEVEL_FULL = 1;

	private static String readInputStreamAsString(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new UnicodeReader(in, "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}

	private String doPostWithAttributestInUrl(String url, List<NameValuePair> nameValuePairs, String key) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();

		boolean first = true;
		for (NameValuePair nvp : nameValuePairs) {
			if (first) {
				url = url + "?";
				first = false;
			} else {
				url = url + "&";
			}
			url = url + nvp.getName() + "=" + URLEncoder.encode(nvp.getValue(), "UTF-8");
		}
		System.out.println(url);
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Authorization", "Bearer " + key);
		httppost.setHeader("ContentType", "text/xml");
		HttpResponse response = httpclient.execute(httppost);
		return readInputStreamAsString(response.getEntity().getContent());
	}

	private String doGet(String url, List<NameValuePair> nameValuePairs, String key) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(url);
		httppost.setHeader("Authorization", "Bearer " + key);
		boolean first = true;
		for (NameValuePair nvp : nameValuePairs) {
			if (first) {
				url = url + "?";
				first = false;
			} else {
				url = url + "&";
			}
			url = url + nvp.getName() + "=" + URLEncoder.encode(nvp.getValue(), "UTF-8");
		}
		System.out.println(url);
		HttpResponse response = httpclient.execute(httppost);
		return readInputStreamAsString(response.getEntity().getContent());
	}

	private static String getAPIKey() throws TranslationException {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("https://datamarket.accesscontrol.windows.net/v2/OAuth2-13");

		String responseText = "";
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("client_id", "TranslateAndLearnMgoreckiNet"));
			nameValuePairs.add(new BasicNameValuePair("client_secret", "N98RnTMlWsrhTZ69RJ67D4fbFvFAIeGapcUA9ZGB0fY="));
			nameValuePairs.add(new BasicNameValuePair("scope", "http://api.microsofttranslator.com"));
			nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			responseText = readInputStreamAsString(response.getEntity().getContent());

		} catch (ClientProtocolException e) {
			throw new TranslationException(e);
		} catch (IOException e) {
			throw new TranslationException(e);
		}

		String key;
		try {
			JSONObject jsonKey = new JSONObject(responseText);
			key = jsonKey.getString("access_token");
		} catch (JSONException e) {
			throw new TranslationException(e);
		}
		return key;
	}

	@Override
	public String translate(String text, String langFrom, String langTo) throws TranslationException {

		String key = getAPIKey();

		String translatedJson = "";
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("text", text));
			nameValuePairs.add(new BasicNameValuePair("to", langTo));
			nameValuePairs.add(new BasicNameValuePair("from", langFrom));
			nameValuePairs.add(new BasicNameValuePair("maxTranslations", "255"));

			translatedJson = doPostWithAttributestInUrl("https://api.microsofttranslator.com/V2/Ajax.svc/GetTranslations", nameValuePairs, key);

		} catch (ClientProtocolException e) {
			throw new TranslationException(e);
		} catch (IOException e) {
			throw new TranslationException(e);
		}

		String translated = makeTranslationsFromJson(translatedJson, INFO_LEVEL_FULL);

		return translated;
	}

	private String makeTranslationsFromJson(String translatedJson, int infoLevel) {
		StringBuffer formattedText = new StringBuffer();

		Log.d("BING_PROVIDER",translatedJson);
		try {
			JSONObject jsonResultArray = new JSONObject(translatedJson);
			JSONArray translationArray = jsonResultArray.getJSONArray("Translations");

			for (int i = 0; i < translationArray.length(); i++) {
				if (i > 0) {
					formattedText.append("\n");
				}
				JSONObject translationRecord = translationArray.getJSONObject(i);
				formattedText.append((i + 1)).append(". ");
				formattedText.append(translationRecord.get("TranslatedText"));
				if(INFO_LEVEL_FULL >= infoLevel){
					formattedText.append(" R:").append(translationRecord.get("Rating"));
					formattedText.append(" MD:").append(translationRecord.get("MatchDegree"));
					formattedText.append(" C:").append(translationRecord.get("Count"));
				}

			}
		} catch (JSONException e) {
			// replace buffer with original text
			formattedText.delete(0, formattedText.length()).append(translatedJson);
		}

		return formattedText.toString();
	}

	public String getSupportedLangauges() throws TranslationException {

		String key = getAPIKey();

		String translated = "";
		try {

			translated = doGet("http://api.microsofttranslator.com/V2/Http.svc/GetLanguagesForTranslate", null, key);

		} catch (ClientProtocolException e) {
			throw new TranslationException(e);
		} catch (IOException e) {
			throw new TranslationException(e);
		}

		return translated;
	}

}
