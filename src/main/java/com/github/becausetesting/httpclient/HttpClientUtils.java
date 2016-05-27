package com.github.becausetesting.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.becausetesting.apache.commons.IOUtils;
import com.github.becausetesting.apache.commons.StringUtils;
import com.github.becausetesting.collections.MultiValueMap;
import com.github.becausetesting.http.HttpsCert;
import com.github.becausetesting.httpclient.bean.Auth;
import com.github.becausetesting.httpclient.bean.BasicAuth;
import com.github.becausetesting.httpclient.bean.CookiesStore;
import com.github.becausetesting.httpclient.bean.DigestAuth;
import com.github.becausetesting.httpclient.bean.HttpVersion;
import com.github.becausetesting.httpclient.bean.NTLMAuth;
import com.github.becausetesting.httpclient.bean.OAuth2;
import com.github.becausetesting.httpclient.bean.ProxyConfig;
import com.github.becausetesting.httpclient.bean.RequestEntity;
import com.github.becausetesting.httpclient.bean.RequestEntityFile;
import com.github.becausetesting.httpclient.bean.RequestEntityMultipart;
import com.github.becausetesting.httpclient.bean.RequestEntityMultipart.MultipartMode;
import com.github.becausetesting.httpclient.bean.RequestEntitySimple;
import com.github.becausetesting.httpclient.bean.RequestEntityString;
import com.github.becausetesting.httpclient.bean.SSLRequest;
import com.github.becausetesting.httpclient.bean.SSLRequest.SSLHostnameVerifier;
import com.github.becausetesting.properties.PropertiesUtils;

import freemarker.template.utility.StringUtil;

@SuppressWarnings("deprecation")
public class HttpClientUtils {

	enum Type {
		GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS, TRACE;
	}

	private static final Logger log = LogManager.getLogger(HttpClientUtils.class.getName());

	private static CloseableHttpClient httpClient;
	public static HttpResponse httpResponse;
	public static Response response;

	private static HttpClientContext httpClientContext;
	private static CookieStore responseCookieStore;

	/*
	 * / https://github.com/wiztools/rest-client/blob/master/restclient-lib/src/
	 * main/java/org/wiztools/restclient/HTTPClientRequestExecuter.java
	 */
	public static Response getResponse(Request request) throws IOException {
		response = new Response();
		//HttpsCert.ignoreCert();
		// Create all the builder objects:
		final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
		// Needed for specifying HTTP pre-emptive authentication:
		httpClientContext = HttpClientContext.create();

		final RequestBuilder requestBuilder;

		switch (request.getMethod()) {
		case GET:
			requestBuilder = RequestBuilder.get();
			break;
		case POST:
			requestBuilder = RequestBuilder.post();
			break;
		case PUT:
			requestBuilder = RequestBuilder.put();
			break;
		case PATCH:
			requestBuilder = RequestBuilder.create("PATCH");
			break;
		case DELETE:
			requestBuilder = RequestBuilder.delete();
			break;
		case HEAD:
			requestBuilder = RequestBuilder.head();
			break;
		case OPTIONS:
			requestBuilder = RequestBuilder.options();
			break;
		case TRACE:
			requestBuilder = RequestBuilder.trace();
			break;
		default:
			throw new IllegalStateException("Method not defined!");
		}

		// Retry handler (no-retries):
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

		// url
		URL url = request.getUrl();
		final String urlHost = url.getHost();
		final int urlPort = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
		final String urlProtocol = url.getProtocol();
		final String urlStr = url.toString();
		requestBuilder.setUri(urlStr);

		// Set HTTP version:
		HttpVersion httpVersion = request.getHttpVersion();
		ProtocolVersion protocolVersion = httpVersion == HttpVersion.HTTP_1_1 ? new ProtocolVersion("HTTP", 1, 1)
				: new ProtocolVersion("HTTP", 1, 0);
		requestBuilder.setVersion(protocolVersion);

		// Set request timeout (default 1 minute--60000 milliseconds)
		String httpclientFile = "httpclient.properties";
		PropertiesUtils.setBundle(httpclientFile);
		String timeout = PropertiesUtils.getBundleString("DEFAULT_TIMEOUT_MILLIS");
		requestConfigBuilder.setConnectionRequestTimeout(Integer.parseInt(timeout));

		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		// PoolingHttpClientConnectionManager cm = new
		// PoolingHttpClientConnectionManager();
		// cm.setMaxTotal(100);
		// proxy
		ProxyConfig proxy = ProxyConfig.getInstance();
		proxy.acquire();
		if (proxy.isEnabled()) {
			final HttpHost proxyHost = new HttpHost(proxy.getHost(), proxy.getPort(), "http");
			if (proxy.isAuthEnabled()) {
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()),
						new UsernamePasswordCredentials(proxy.getUsername(), new String(proxy.getPassword())));
				httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
			}
			httpClientBuilder.setProxy(proxyHost);
		}
		proxy.release();

		// Get request headers
		boolean createAuthorizationHeader = true;
		MultiValueMap<String, String> headerdata = request.getHeaders();
		for (String key : headerdata.keySet()) {
			if (key.equals("Authorization")) {
				createAuthorizationHeader = false;
			}
			for (String value : headerdata.get(key)) {
				Header header = new BasicHeader(key, value);
				requestBuilder.addHeader(header);
			}
		}

		// HTTP Authentication header
		if (createAuthorizationHeader) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			Auth auth = request.getAuth();
			AuthCache authCache = null;
			List<String> authPrefs = new ArrayList<>();
			if (auth != null && auth instanceof BasicAuth) {
				authPrefs.add(AuthSchemes.BASIC);
				BasicAuth basicAuth = (BasicAuth) auth;
				// new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,
				// AuthScope.ANY_REALM,AuthScope.ANY_SCHEME)
				credsProvider.setCredentials(AuthScope.ANY,
						new UsernamePasswordCredentials(basicAuth.getUsername(), basicAuth.getPassword()));
				httpClientBuilder.setDefaultCredentialsProvider(credsProvider);

				// preemptive mode:
				if (basicAuth.isPreemptive()) {
					authCache = new BasicAuthCache();
					AuthSchemeBase authScheme = auth instanceof BasicAuth ? new BasicScheme() : new DigestScheme();
					// Generate BASIC scheme object and add it to the local
					// auth cache
					authCache.put(new HttpHost(urlHost, urlPort, urlProtocol), authScheme);
					// Add AuthCache to the execution context ,in future you can
					// use
					// this stored credentail in HttpContext
					httpClientContext.setAuthCache(authCache);
				}
			}
			// NTLM:
			else if (auth != null && auth instanceof NTLMAuth) {
				authPrefs.add(AuthSchemes.NTLM);
				NTLMAuth a = (NTLMAuth) auth;
				String uid = a.getUsername();
				String pwd = new String(a.getPassword());

				credsProvider.setCredentials(AuthScope.ANY,
						new NTCredentials(uid, pwd, a.getWorkstation(), a.getDomain()));
				httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
			}
			// Digest auth
			else if (auth != null && auth instanceof DigestAuth) {
				authPrefs.add(AuthSchemes.DIGEST);
				DigestAuth basicAuth = (DigestAuth) auth;
				// new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,
				// AuthScope.ANY_REALM,AuthScope.ANY_SCHEME)
				credsProvider.setCredentials(AuthScope.ANY,
						new UsernamePasswordCredentials(basicAuth.getUsername(), basicAuth.getPassword()));
				httpClientBuilder.setDefaultCredentialsProvider(credsProvider);

				// preemptive mode:
				if (basicAuth.isPreemptive()) {
					authCache = new BasicAuthCache();
					AuthSchemeBase authScheme = auth instanceof DigestAuth ? new DigestScheme() : new BasicScheme();
					// Generate BASIC scheme object and add it to the local
					// auth cache
					authCache.put(new HttpHost(urlHost, urlPort, urlProtocol), authScheme);
					// Add AuthCache to the execution context ,in future you can
					// use
					// this stored credentail in HttpContext
					// httpClientContext = HttpClientContext.create();
					httpClientContext.setAuthCache(authCache);
					// httpContext = httpClientContext;
				}
			}

			requestConfigBuilder.setTargetPreferredAuthSchemes(authPrefs);
			// Credentials credentials =
			// credsProvider.getCredentials(AuthScope.ANY);
			// String usernamepassword =
			// credentials.getUserPrincipal().getName();
			// logger.info("Credential User: " + usernamepassword);
			// Authorization Header Authentication:
			// OAuth2 use this method Add the AuthorizationHeaderAuth a =
			// (AuthorizationHeaderAuth) auth; for OAuth
			if (auth != null) {
				String authHeader = auth.getAuthorizationHeaderValue();
				if (StringUtils.isNotEmpty(authHeader)) {
					Header header = new BasicHeader("Authorization", authHeader);
					requestBuilder.addHeader(header);
				}
			}
		}
		// Cookies
		// Set cookie policy:
		requestConfigBuilder.setCookieSpec(CookieSpecs.DEFAULT);
		// Add to CookieStore:
		List<HttpCookie> cookies = request.getCookies();
		if (cookies.size() > 0) {
			responseCookieStore = new CookiesStore();
			for (HttpCookie cookie : cookies) {
				BasicClientCookie basicClientCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
				basicClientCookie.setVersion(cookie.getVersion());
				basicClientCookie.setDomain(urlHost);
				basicClientCookie.setPath("/");
				responseCookieStore.addCookie(basicClientCookie);
			}
		} else {
			if (responseCookieStore == null) { // first send the request
				responseCookieStore = new CookiesStore();
			}
		}

		// Attach store to client:
		httpClientBuilder.setDefaultCookieStore(responseCookieStore);
		// httpContext.setCookieStore(responseCookieStore);
		// POST/PUT/PATCH/DELETE method specific logic
		// Create and set RequestEntity
		RequestEntity bean = request.getBody();
		if (bean != null) {
			try {
				HttpEntity httpEntity = null;
				if (bean instanceof RequestEntitySimple) {
					httpEntity = (HttpEntity) bean;
					requestBuilder.setEntity(httpEntity);
				} else if (bean instanceof RequestEntityMultipart) {
					RequestEntityMultipart multipart = (RequestEntityMultipart) bean;
					MultipartEntityBuilder meb = MultipartEntityBuilder.create();

					// multipart/mixed / multipart/form-data:
					meb.setMimeSubtype(multipart.getSubtype().toString());

					// Format:
					MultipartMode mpMode = multipart.getMode();
					switch (mpMode) {
					case BROWSER_COMPATIBLE:
						meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
						break;
					case RFC_6532:
						meb.setMode(HttpMultipartMode.RFC6532);
						break;
					case STRICT:
						meb.setMode(HttpMultipartMode.STRICT);
						break;
					}

					// Parts:
					for (RequestEntitySimple part : multipart.getBody()) {
						ContentBody cb = null;

						if (part instanceof RequestEntityString) {
							RequestEntityString requestEntityString = (RequestEntityString) part;
							InputStream body = requestEntityString.getContent();
							String contentTypeValue = requestEntityString.getContentType().getValue();
							ContentType contentType = ContentType.create(contentTypeValue, Charset.forName("UTF-8"));
							if (contentType != null) {
								cb = new InputStreamBody(body, contentType);
							} else {
								cb = new InputStreamBody(body, ContentType.DEFAULT_TEXT);
							}

						} else if (part instanceof RequestEntityFile) {
							RequestEntityFile requestEntityFile = (RequestEntityFile) part;
							InputStream body = requestEntityFile.getContent();

							String contentTypeValue = requestEntityFile.getContentType().getValue();
							ContentType contentType = ContentType.create(contentTypeValue, Charset.forName("UTF-8"));
							if (contentType != null) {
								cb = new InputStreamBody(body, contentType);
							} else {
								cb = new InputStreamBody(body, ContentType.DEFAULT_BINARY);
							}
						}

						FormBodyPartBuilder bodyPart = FormBodyPartBuilder.create().setName(part.toString())
								.setBody(cb);
						/*
						 * MultiValueMap<String, String> fields =
						 * part.getFields(); for (String key : fields.keySet())
						 * { for (String value : fields.get(key)) {
						 * bodyPart.addField(key, value); } }
						 */
						meb.addPart(bodyPart.build());
					}

					requestBuilder.setEntity(meb.build());
				}

			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}

		}

		// SSL
		// Set the hostname verifier:
		final SSLRequest sslReq = request.getSslRequest();
		if (sslReq != null) {
			SSLHostnameVerifier hostNameVerifier = sslReq.getHostNameVerifier();
			final HostnameVerifier hcVerifier;
			switch (hostNameVerifier) {
			case ALLOW_ALL:
				hcVerifier = new NoopHostnameVerifier();
				break;
			case STRICT:
			default:
				hcVerifier = new DefaultHostnameVerifier();
				break;
			}

			// Register the SSL Scheme manually
			KeyStore trustStore = null;
			SSLConnectionSocketFactory sf = null;
			try {
				final KeyStore keyStore = sslReq.getKeyStore() == null ? null : sslReq.getKeyStore().getKeyStore();

				final TrustStrategy trustStrategy = sslReq.isTrustSelfSignedCert() ? new TrustSelfSignedStrategy()
						: null;
				trustStore = sslReq.getTrustStore() == null ? null : sslReq.getTrustStore().getKeyStore();

				SSLContext ctx = new SSLContextBuilder()
						.loadKeyMaterial(keyStore,
								sslReq.getKeyStore() != null ? sslReq.getKeyStore().getPassword() : null)
						.loadTrustMaterial(trustStore, trustStrategy).setSecureRandom(null).useProtocol("TLS").build();
				sf = new SSLConnectionSocketFactory(ctx, hcVerifier);
			} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException
					| KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			httpClientBuilder.setSSLSocketFactory(sf);
		}

		// How to handle redirects:
		boolean followRedirect = request.isFollowRedirect();
		requestConfigBuilder.setRedirectsEnabled(followRedirect); // Default is
																	// true,but
																	// still not
																	// work for
																	// POST,DELETE,only
																	// for GET
		// LaxRedirectStrategy will automatically redirect HEAD, GET, and POST
		// requests. For a stricter implementation, use
		// DefaultRedirectStrategy.(GET,HEAD)
		httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
		// httpClientBuilder.setRedirectStrategy(new DefaultRedirectStrategy());

		// requestConfigBuilder.setConnectTimeout(connectTimeout);
		// Now Execute:
		RequestConfig rc = requestConfigBuilder.build();
		requestBuilder.setConfig(rc);
		HttpUriRequest httpUriRequest = requestBuilder.build();
		if (!WinHttpClients.isWinAuthAvailable()) {
			log.error("Integrated Win auth is not supported!!!");
		} else {
			httpClient = WinHttpClients.createDefault();
		}
		httpClient = httpClientBuilder.build();

		long startTime = System.currentTimeMillis();
		httpResponse = httpClient.execute(httpUriRequest, httpClientContext);
		long endTime = System.currentTimeMillis();

		responseCookieStore = httpClientContext.getCookieStore();

		URI finalURI;
		List<URI> redirectURIs = httpClientContext.getRedirectLocations();
		if (redirectURIs != null && !redirectURIs.isEmpty()) {
			for (URI redirectURI : redirectURIs) {
				System.out.println("Redirect URI: " + redirectURI);
			}
			finalURI = redirectURIs.get(redirectURIs.size() - 1);
		}

		response.setExecutionTime(endTime - startTime);
		response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		response.setStatusLine(httpResponse.getStatusLine().toString());

		final Header[] responseHeaders = httpResponse.getAllHeaders();
		List<HttpCookie> httpCookies = new ArrayList<>();
		for (Header header : responseHeaders) {
			response.setHeader(header.getName(), header.getValue());
		}

		// Response body:
		final HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			if (request.isIgnoreResponseBody()) {
				EntityUtils.consumeQuietly(entity);
			} else {
				InputStream is = entity.getContent();
				try {
					byte[] responseBody = IOUtils.toByteArray(is);
					if (responseBody != null) {
						response.setResponseBody(responseBody);
					}
				} catch (IOException ex) {

				}
			}
		}
		return response;
	}
}
