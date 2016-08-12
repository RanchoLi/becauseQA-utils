package com.github.becauseQA.httpclient.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class CookiesStore implements CookieStore {

	private List<Cookie> cookies = new ArrayList<Cookie>();

	@Override
	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		cookies.add(cookie);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		cookies.clear();
	}

	@Override
	public boolean clearExpired(Date arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Cookie> getCookies() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(cookies);
	}

}
