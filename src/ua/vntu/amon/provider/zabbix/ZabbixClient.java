package ua.vntu.amon.provider.zabbix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

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
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import ua.vntu.amon.json.converting.*;

//import ua.vntu.amon.json.converting.User;

public class ZabbixClient {

	private static final String CONTENT_TYPE = "application/json";

	private static final int PROCESSORS = 20;

	private final int CONNECTION_TIMEOUT = 30000;

	public String tokenSession;

	protected final HttpClient httpclient;
	protected final ObjectMapper mapper;

	private final String url = "http://192.168.248.9/api_jsonrpc.php"; // "http://192.168.56.101/api_jsonrpc.php";

	public ZabbixClient() {
		mapper = new ObjectMapper();

		DeserializationConfig deserializationConf = mapper
				.getDeserializationConfig();
		deserializationConf = deserializationConf
				.without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

		mapper.setDeserializationConfig(deserializationConf);

		SerializationConfig serializationConf = mapper.getSerializationConfig();
		// serializationConf =
		// serializationConf.withSerializationInclusion(Inclusion.NON_NULL);
		mapper.setSerializationConfig(serializationConf);
		httpclient = createHttpClient();
	}
	
	private <T> T send(Object message, Class<T> clazz) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", CONTENT_TYPE);
		post.getParams().setIntParameter("http.socket.timeout",
				CONNECTION_TIMEOUT);

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		mapper.writeValue(outStream, message);
		post.setEntity(new ByteArrayEntity(outStream.toByteArray()));
		HttpResponse response = httpclient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("Request failed to " + url
					+ "'with HTTP code:" + statusCode);
		}

		ByteArrayOutputStream jsonOut = new ByteArrayOutputStream();
		response.getEntity().writeTo(jsonOut);

        System.out.println(">>" + new String(jsonOut.toByteArray()));

		return mapper.readValue(jsonOut.toByteArray(), clazz);
	}

	public Object register(String login, String password) {
		BaseResult<String> responseAuthRequest;
		try {
			responseAuthRequest = send(new AuthRequest(login, password), BaseResult.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

		tokenSession = responseAuthRequest.getResult();
		System.out.println(tokenSession);
		return responseAuthRequest;
	}

	public GetHostsResponse hosting(String output, String sortfield) {
		GetHost getHost = new GetHost(output, sortfield);
		getHost.setAuth(tokenSession);

        GetHostsResponse responseGetHost;
		try {
			responseGetHost = send(getHost, GetHostsResponse.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println(Arrays.toString(responseGetHost.getResult().toArray()));

		return responseGetHost;
	}
	
	// ----------------------------------------------------------------------------
	private HttpClient createHttpClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		// turn ssl verification off
		try {
			SSLContext sslContext = SSLContext.getInstance("ssl");
			sslContext.init(null,
					new TrustManager[] { new DumbTrustManager() },
					new SecureRandom());
			SSLSocketFactory factory = new SSLv3SocketFactory(sslContext,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			schemeRegistry.register(new Scheme("https", 443, factory));
		} catch (Exception e) {
			e.printStackTrace();
		}

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		cm.setMaxTotal(PROCESSORS);
		cm.setDefaultMaxPerRoute(PROCESSORS);

		return new DefaultHttpClient(cm);
	}

	public static void main(String[] args) throws Exception {
		/*
		 * ZabbixClient client = new ZabbixClient(); LoginForm login = new
		 * LoginForm(); client.register(login.getName(), login.getPassword());
		 * client.register2("extend", "name");
		 */

	}

	static class DumbTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	static class SSLv3SocketFactory extends SSLSocketFactory {

		public SSLv3SocketFactory(SSLContext sslContext,
				X509HostnameVerifier verifier) {
			super(sslContext, verifier);
		}

		@Override
		public Socket createSocket(HttpParams params) throws IOException {
			SSLSocket socket = (SSLSocket) super.createSocket(params);
			socket.setEnabledProtocols(new String[] { "SSLv3" });

			return socket;
		}
	}

	static class BaseResponse {
		private int status;
		private String message;

		public BaseResponse() {
		}

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

	public String getTokenSession() {
		return tokenSession;
	}

	public void setTokenSession(String tokenSession) {
		this.tokenSession = tokenSession;
	}
}
