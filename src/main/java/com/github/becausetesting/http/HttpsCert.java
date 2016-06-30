/**
 * Project Name:commons
 * File Name:HttpsCert.java
 * Package Name:becausetesting.webserivce
 * Date:Apr 16, 201611:30:00 PM
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becausetesting.http;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * ClassName:HttpsCert  
 * Function: TODO ADD FUNCTION.  
 * Reason:	 TODO ADD REASON.  
 * Date:     Apr 16, 2016 11:30:00 PM 
 * @author   Administrator
 * @version  1.0.0
 * @since    JDK 1.8	 
 */
public class HttpsCert {

	


	/**Disable Certificate Validation,add the self-signed certificate to your JVM truststore
	 * ignoreCert: ignore the cert
	 * @author alterhu2020@gmail.com
	 * @since JDK 1.8
	 */
	public static void ignoreCert() {
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new DefaultTrustManager() },
					new SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HostnameVerifier hv = new HostnameVerifier() {

				@Override
				public boolean verify(String urlHostName, SSLSession session) {
					// TODO Auto-generated method stub
					if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
						System.out.println("Warning: URL host '" + urlHostName
								+ "' is different to SSLSession host '"
								+ session.getPeerHost() + "'.");
					}
					return true;
				}

			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			// SSLContext.setDefault(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
			return;

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
			return;

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}


