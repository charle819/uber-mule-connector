package org.mule.modules.uber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Invocation.Builder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ReconnectOn;
import org.mule.api.annotations.oauth.OAuthPostAuthorization;
import org.mule.api.annotations.oauth.OAuthProtected;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.rest.HttpMethod;
import org.mule.api.annotations.rest.RestCall;
import org.mule.api.annotations.rest.RestHeaderParam;
import org.mule.api.annotations.rest.RestQueryParam;
import org.mule.modules.uber.config.ConnectorConfig;

import com.entity.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Connector(name = "uber", friendlyName = "Uber")
public abstract class UberConnector {

	@Config
	ConnectorConfig config;

	@Processor
	@OAuthProtected
	@ReconnectOn(exceptions= {Exception.class})
	@RestCall(uri= "https://api.uber.com/v1.2/history",method=HttpMethod.GET)
	public abstract void travelInfo() throws Exception;
	
//	@Processor
//	@OAuthProtected
//	@ReconnectOn(exceptions = {Exception.class})
//	@RestCall(uri="https://api.uber.com/v1.2/me",method=HttpMethod.GET)
//	public abstract void getMyDetails(@RestQueryParam("access_token") String accessToken) throws IOException;
//	
	
	
	/**
	 * 
	 * The given below method is used to obtain user's information and the output is obtained in the form of Object 
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Processor
	@OAuthProtected
	public User userData() throws JsonParseException, JsonMappingException, IOException
	{
		String url = "https://api.uber.com/v1.2/me";
		String response  =  enterResourceUrl(url).get(String.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		User user = mapper.readValue(response, User.class);

		System.out.println(user);
		
		return user;
	}
	
	
	
	/**
	 * The given method is used to for adding Accesstoken in the header of the URl
	 * @param url
	 * @return
	 */
	public Builder enterResourceUrl(String url)
	{
		String Authorization = "Bearer " + config.getAccessToken();
		WebTarget target = ClientBuilder.newClient().target(url);
		Builder basicRequest = target.request()
	               .header("Authorization", Authorization)
	               .accept("application/json");
		
		return basicRequest;
	}
	
	
	
	
	
	
	@Processor
	@OAuthProtected
	@ReconnectOn(exceptions = { Exception.class })
	public void getHistory() throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpGet getrequest = new HttpGet("https://api.uber.com/v1.2/history");
		getrequest.addHeader("Authorization","Bearer "+config.getAccessToken());

		HttpResponse response = httpClient.execute(getrequest);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		System.out.println("This is the response : "+response);
		System.out.println("This is the content of the response : "+response.getEntity());
		System.out.println("This is the content pf the response : "+response.getEntity().getContent());
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Uber ....Histor of User : \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		httpClient.getConnectionManager().shutdown();
		
		
		
	}

	@OAuthPostAuthorization
	public void postAuthorize() {
		// This method is called after authorization finishes. Remove if not
		// required
	}

	public ConnectorConfig getConfig() {
		return config;
	}

	public void setConfig(ConnectorConfig config) {
		this.config = config;
	}

}