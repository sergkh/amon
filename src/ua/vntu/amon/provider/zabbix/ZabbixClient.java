package ua.vntu.amon.provider.zabbix;

import java.io.ByteArrayOutputStream;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.OutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


import ua.vntu.amon.json.converting.AuthRequest;
import ua.vntu.amon.json.converting.GetHost;
import ua.vntu.amon.json.converting.ResponseAuthRequest;

//import ua.vntu.amon.json.converting.User;

public class ZabbixClient  {

	private static final String CONTENT_TYPE = "application/json";

	private static final int PROCESSORS = 20;
	
	private final int CONNECTION_TIMEOUT = 30000;
	
	public String auth;

	protected final HttpClient httpclient;
	protected final ObjectMapper mapper;
	
	private final String url = "http://192.168.56.101/api_jsonrpc.php";
	
	public ZabbixClient() {
		mapper = new ObjectMapper();
		
		DeserializationConfig deserializationConf = mapper.getDeserializationConfig();		
		deserializationConf =  deserializationConf.without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mapper.setDeserializationConfig(deserializationConf);
		
		SerializationConfig serializationConf = mapper.getSerializationConfig();
		// serializationConf = serializationConf.withSerializationInclusion(Inclusion.NON_NULL);
		mapper.setSerializationConfig(serializationConf);
		
		httpclient = createHttpClient();
	}
	
	public Object register(String login, String password) throws IOException {
		
		AuthRequest author = new AuthRequest(login, password);
		ResponseAuthRequest responseAuthRequest=new ResponseAuthRequest();
		
		send(author, responseAuthRequest, author.getTitle());
		
		return 1; //send(author, Object.class, author.getAuth());
		
				
	} 
	
	public Object register2(String outputting, String sortfilding) throws IOException {
		GetHost gethoster = new GetHost(outputting,sortfilding);
		gethoster.setAuth(auth);
		System.out.println();
		System.out.println( gethoster.getAuth());
		return send(gethoster, Object.class, gethoster.getTitle());
		
	}
	
	// I do special for ResponseAuthRequest 
	private <T> ResponseAuthRequest send(Object message, ResponseAuthRequest responseAuthRequest, String title) throws JsonGenerationException, JsonMappingException, IOException {
		HttpPost post = new HttpPost(url);
		
		post.setHeader("Content-Type", CONTENT_TYPE);

		// increase connection timeout 
		post.getParams().setIntParameter("http.socket.timeout", CONNECTION_TIMEOUT);
	
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		mapper.writeValue(outStream, message);
		//System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(user));
		post.setEntity(new ByteArrayEntity(outStream.toByteArray()));
		
		HttpResponse resp = httpclient.execute(post);
		
		int statusCode = resp.getStatusLine().getStatusCode();
		
		if(statusCode != 200) {
			throw new RuntimeException("Request failed to '" + url + "' with HTTP code: " + statusCode);
		}
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		resp.getEntity().writeTo(bout);
		
		// bout ������ �� �����
		
		mapper.writeValue(new File("d:\\" + title + ".json"), new String(bout.toByteArray() ));
		System.out.println(">>" + new String(bout.toByteArray())); 
		
		responseAuthRequest= mapper.readValue(bout.toByteArray(), ResponseAuthRequest.class ); 
		auth=responseAuthRequest.getResult();
		System.out.println(auth);
		return responseAuthRequest;
		   
	}
	
	//--------------------------------------------------------------------------------------------------
	
	private <T> T send(Object message, Class<T> clazz, String title) throws JsonGenerationException, JsonMappingException, IOException {
		HttpPost post = new HttpPost(url);
		
		post.setHeader("Content-Type", CONTENT_TYPE);

		// increase connection timeout 
		post.getParams().setIntParameter("http.socket.timeout", CONNECTION_TIMEOUT);
	
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		mapper.writeValue(outStream, message);
		//System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(user));
		post.setEntity(new ByteArrayEntity(outStream.toByteArray()));
		
		HttpResponse resp = httpclient.execute(post);
		
		int statusCode = resp.getStatusLine().getStatusCode();
		
		if(statusCode != 200) {
			throw new RuntimeException("Request failed to '" + url + "' with HTTP code: " + statusCode);
		}
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		resp.getEntity().writeTo(bout);
		
		// bout ������ �� �����
		
		mapper.writeValue(new File("d:\\" + title + ".json"), new String(bout.toByteArray() ));
		System.out.println(">>" + new String(bout.toByteArray())); 
		
		
		return mapper.readValue(bout.toByteArray(), clazz);  
				
		// System.out.println("Status: " + respData.getStatus());   
	}
	//----------------------------------------------------------------------------
	private HttpClient createHttpClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

		// turn ssl verification off
		try {
			SSLContext sslContext = SSLContext.getInstance("ssl");
			sslContext.init(null, new TrustManager[] { new DumbTrustManager() }, new SecureRandom());
			SSLSocketFactory factory = new SSLv3SocketFactory(sslContext, 
								SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);			
			schemeRegistry.register(new Scheme("https", 443, factory));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(PROCESSORS);
		cm.setDefaultMaxPerRoute(PROCESSORS);

		return new DefaultHttpClient(cm);
	}
	
	public static void main(String[] args) throws Exception {
		ZabbixClient client = new ZabbixClient();
		client.register("Admin", "zabbix");
		client.register2("extend", "name");
	}
	
	
	static class DumbTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {}

		@Override
		public void checkServerTrusted(
				X509Certificate[] arg0, String arg1)
				throws CertificateException {}

		@Override
		public X509Certificate[] getAcceptedIssuers() { return null; }
	}

	
	static class SSLv3SocketFactory extends SSLSocketFactory {

		public SSLv3SocketFactory(SSLContext sslContext, X509HostnameVerifier verifier) {
		    super(sslContext, verifier);
		}

		@Override
		public Socket createSocket(HttpParams params) throws IOException {
			SSLSocket socket = (SSLSocket) super.createSocket(params);
		    socket.setEnabledProtocols(new String[] {"SSLv3"});

		    return socket;
		}
	}

	static class BaseResponse {
		private int status;
		private String message;
		
		public BaseResponse() {}

		public void setStatus(int status) {
			this.status = status;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}
	}
}
