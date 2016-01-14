package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class NemoClient {
	private static final String COMMAND_PROMPT = "";
	private static final String COMMAND_EXIT = "exit";

	private static final String URI_PATH_USER = "user";
	// private static final String URI_PATH_NEMO = "nemo";

	private static final String JSON_KEY_USERNAME = "username";
	private static final String JSON_KEY_PASSWORD = "password";
	// private static final String JSON_KEY_USERROLE = "UserRole";
	private static final String JSON_KEY_NEMO = "NEMO";

	private static final String INFO_PROMPT_INFO = "Info: ";
	private static final String INFO_PROMPT_ERROR = "Error: ";

	private static final String RESPONSE_CONTENT_USER_SUCCEEDED = "User Creation Succeeded!";
	private static final String RESPONSE_CONTENT_USER_NOT_EXIST = "User Does Not Exists!";
	private static final String RESPONSE_CONTENT_USER_PWD_WRONG = "User Pwd Error!";
	private static final String RESPONSE_CONTENT_NEMO_SUCCEEDED = "NEMO Executation Succeeded!";
	private static final String RESPONSE_CONTENT_UNKNOWN_EXCEPTION = "<html>";

	public static String generateBaseURIOfNEMOEngine(String ipAddrOfNEMOEngine) {
		return "http://" + ipAddrOfNEMOEngine + ":10081/hyp/vn";
	}

	private static HttpPost generateHttpPostRequest_User(
			String baseURIOfNEMOEngine, String username, String password,
			String userRole) {
		HttpPost httpPost = new HttpPost(baseURIOfNEMOEngine + URI_PATH_USER);
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(JSON_KEY_USERNAME, username);
		jsonObject.put(JSON_KEY_PASSWORD, password);
		// jsonObject.put(JSON_KEY_USERROLE, userRole);

		try {
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(new StringEntity(jsonObject.toString()));
		} catch (UnsupportedEncodingException exception) {

		}

		return httpPost;
	}


	public static String send(String url, String content) {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		AuthScope authScope = new AuthScope(AuthScope.ANY_HOST,
				AuthScope.ANY_PORT);
		UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(
				"admin", "admin");
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(authScope,
				usernamePasswordCredentials);
		httpClient.setCredentialsProvider(credentialsProvider);
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-type", "application/json");
		try {
			post.setEntity(new StringEntity(content, ContentType
					.create("application/json")));

			BufferedReader reader;

			HttpResponse response = httpClient.execute(post);

			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			return reader.readLine();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Exception while send : " + url + "\r\n" + content;
	}

}
