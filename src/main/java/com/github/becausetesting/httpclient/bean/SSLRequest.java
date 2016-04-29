package com.github.becausetesting.httpclient.bean;

import java.io.Serializable;

public class SSLRequest implements Serializable {

	public enum SSLHostnameVerifier {
		STRICT("Strict"), @Deprecated BROWSER_COMPATIBLE("Browser Compatible"), ALLOW_ALL("Allow All");

		private final String displayName;

		SSLHostnameVerifier(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return displayName;
		}

		public static SSLHostnameVerifier[] getAll() {
			return new SSLHostnameVerifier[] { STRICT, BROWSER_COMPATIBLE, ALLOW_ALL };
		}
	}

	
	private SSLHostnameVerifier hostNameVerifier = SSLHostnameVerifier.ALLOW_ALL;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8914248174464804923L;
	
	private SSLKeyStore trustStore;
    private SSLKeyStore keyStore;
    
    private boolean trustSelfSignedCert = true;

	/**
	 * @return the trustStore
	 */
	public SSLKeyStore getTrustStore() {
		return trustStore;
	}

	/**
	 * @param trustStore the trustStore to set
	 */
	public void setTrustStore(SSLKeyStore trustStore) {
		this.trustStore = trustStore;
	}

	/**
	 * @return the keyStore
	 */
	public SSLKeyStore getKeyStore() {
		return keyStore;
	}

	/**
	 * @param keyStore the keyStore to set
	 */
	public void setKeyStore(SSLKeyStore keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * @return the trustSelfSignedCert
	 */
	public boolean isTrustSelfSignedCert() {
		return trustSelfSignedCert;
	}

	/**
	 * @param trustSelfSignedCert the trustSelfSignedCert to set
	 */
	public void setTrustSelfSignedCert(boolean trustSelfSignedCert) {
		this.trustSelfSignedCert = trustSelfSignedCert;
	}

	/**
	 * @return the hostNameVerifier
	 */
	public SSLHostnameVerifier getHostNameVerifier() {
		return hostNameVerifier;
	}

	/**
	 * @param hostNameVerifier the hostNameVerifier to set
	 */
	public void setHostNameVerifier(SSLHostnameVerifier hostNameVerifier) {
		this.hostNameVerifier = hostNameVerifier;
	}
    
    
    

}
