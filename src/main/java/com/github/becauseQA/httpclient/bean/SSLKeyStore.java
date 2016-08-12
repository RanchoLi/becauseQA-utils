package com.github.becauseQA.httpclient.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SSLKeyStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7227693271825159929L;

	enum KeyStoreType {
		JKS, PKCS12;
	}

	private File file;
	private KeyStoreType type = KeyStoreType.JKS;
	private char[] password;

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the type
	 */
	public KeyStoreType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(KeyStoreType type) {
		this.type = type;
	}

	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

	public KeyStore getKeyStore()
			throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
		KeyStore store = KeyStore.getInstance(type.name());
		if (file != null) {
			try (FileInputStream instream = new FileInputStream(file)) {
				store.load(instream, password);
			}
		}
		return store;
	}
}
