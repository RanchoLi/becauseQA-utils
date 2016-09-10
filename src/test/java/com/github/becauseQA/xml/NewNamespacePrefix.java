package com.github.becauseQA.xml;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

public class NewNamespacePrefix implements NamespaceContext {

	@Override
	public String getNamespaceURI(String prefix) {
		// TODO Auto-generated method stub
		return "https://github.com/BecauseQA";
	}

	@Override
	public String getPrefix(String namespaceURI) {
		// TODO Auto-generated method stub
		return "URIPrefix";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getPrefixes(String namespaceURI) {
		// TODO Auto-generated method stub
		return null;
	}

}
