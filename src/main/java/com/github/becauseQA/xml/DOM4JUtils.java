/**
 * Project Name:commons
 * File Name:DOM4JUtils.java
 * Package Name:com.github.becauseQA.xml
 * Date:2016年4月16日下午8:34:56
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.github.becauseQA.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.xpath.DefaultXPath;

/**
 * ClassName: DOM4JUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON
 * date: Apr 16, 2016 8:46:32 PM
 *
 * @author Administrator
 * @version 1.0.0
 * @since JDK 1.8
 * @see SAXReader
 * 
 * 
 */
public class DOM4JUtils {

	public static Document document = null;

	public DOM4JUtils(String xmlfile) {
		try {
			// document = reader.read(new
			// ByteArrayInputStream(xml.getBytes("UTF-8")));
			document = new SAXReader().read(new ByteArrayInputStream(xmlfile.getBytes("UTF-8")));
		} catch (DocumentException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DOM4JUtils(InputStream inputStream) {
		try {
			// document = reader.read(new
			// ByteArrayInputStream(xml.getBytes("UTF-8")));
			document = new SAXReader().read(inputStream);
		} catch (DocumentException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public DOM4JUtils(URL url) {
		try {
			document = new SAXReader().read(url);
		} catch (DocumentException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/**
	 * @param node
	 *            the xml node object
	 * @param xpath
	 *            the xpath need to query, if it's namespace should be
	 *            ./ns:subnodename ,if we root element should be //ns
	 * @return found node object
	 */
	public Node getNode(Node node, String xpath) {
		Node foundnode = null;
		Element rootElement = document.getRootElement();
		String namespace = rootElement.getNamespaceURI();
		if (namespace != null) {
			DefaultXPath defaultXPath = new DefaultXPath(xpath);
			Map<String, String> namespaces = new TreeMap<String, String>();
			namespaces.put("ns", namespace);
			defaultXPath.setNamespaceURIs(namespaces);
			foundnode = defaultXPath.selectSingleNode(node);

		} else {
			foundnode = node.selectSingleNode(xpath);
		}
		return foundnode;

	}

	/**
	 * @param node
	 *            the xml node object
	 * @param xpath
	 *            the xpath need to query, if it's namespace should be
	 *            ./ns:subnodename ,if we root element should be //ns
	 * @return found node object
	 */
	public Node getNode(String xpath) {
		Node foundnode = null;
		Element rootElement = document.getRootElement();
		String namespace = rootElement.getNamespaceURI();
		if (namespace != null) {
			DefaultXPath defaultXPath = new DefaultXPath(xpath);
			Map<String, String> namespaces = new TreeMap<String, String>();
			namespaces.put("ns", namespace);
			defaultXPath.setNamespaceURIs(namespaces);
		}
		foundnode = document.selectSingleNode(xpath);

		return foundnode;

	}

	/**
	 * @param node
	 *            the xml node object
	 * @param xpath
	 *            the xpath need to query, here we ignore the namespace,
	 *            like if you want to query the node ,you can use below xpaths
	 *            : //*[local-name()='nodename' and text()='nodetext']
	 *            : //*[count()]
	 * @return found node object
	 */
	public Node getNodeIgnoreNamespace(String xpath) {
		Node foundnode = document.selectSingleNode(xpath);
		return foundnode;

	}
	/**
	 * @param node
	 *            the xml node object
	 * @param xpath
	 *            the xpath need to query, here we ignore the namespace,
	 *            like if you want to query the node ,you can use below xpaths
	 *            : //*[local-name()='nodename' and text()='nodetext']
	 *            
	 * @return found node object
	 */
	@SuppressWarnings("unchecked")
	public List<Node> getNodesIgnoreNamespace(String xpath) {
		 List<Node> selectNodes = document.selectNodes(xpath);
		 return selectNodes;

	}

	/**
	 * @param xpath
	 *            the node object ,if it's namespace should be
	 *            ://ns:nodename/ns:secondnodename
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Node> getNodes(String xpath) {
		// xpath like: //ns:AuthResponse
		List<Node> node = null;
		Element rootElement = document.getRootElement();
		String namespace = rootElement.getNamespaceURI();
		if (namespace != null) {
			DefaultXPath defaultXPath = new DefaultXPath(xpath);
			Map<String, String> namespaces = new TreeMap<String, String>();
			namespaces.put("ns", namespace);
			defaultXPath.setNamespaceURIs(namespaces);
			node = defaultXPath.selectNodes(document);
		} else {
			node = rootElement.selectNodes(xpath);
		}
		return node;

	}

	public String getNodeAttributeValue(String xpath, String attribute) {
		// or use the xpath to get the attribute value：//foo/bar/author/@name
		String attributeValue = null;
		Element rootElement = document.getRootElement();
		String namespace = rootElement.getNamespaceURI();
		if (namespace != null) {
			DefaultXPath defaultXPath = new DefaultXPath(xpath);
			Map<String, String> namespaces = new TreeMap<String, String>();
			namespaces.put("ns", namespace);
			defaultXPath.setNamespaceURIs(namespaces);

			attributeValue = defaultXPath.selectSingleNode(document).valueOf("@" + attribute);
		} else {
			attributeValue = rootElement.selectSingleNode(xpath).valueOf("@" + attribute);
		}
		return attributeValue;
	}

	public void setNodeValue(String xpath, String value) {
		Element rootElement = document.getRootElement();
		String namespace = rootElement.getNamespaceURI();
		if (namespace != null) {
			DefaultXPath defaultXPath = new DefaultXPath(xpath);
			Map<String, String> namespaces = new TreeMap<String, String>();
			namespaces.put("ns", namespace);
			defaultXPath.setNamespaceURIs(namespaces);
			defaultXPath.selectSingleNode(document).setText(value);
		} else {
			rootElement.selectSingleNode(xpath).setText(value);
		}
	}

	public void writeSampleXml() {
		// create the document
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("sample");
		document.setRootElement(root);

		Element firstelement = root.addElement("hello");
		firstelement.setText("testhello");
		firstelement.addAttribute("name", "tester");

		Element secondelement = root.addElement("hello2");
		secondelement.setText("testhello2: http://www.cnblogs.com/mengdd/archive/2013/06/05/3119927.html");

		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");
		try {
			XMLWriter console = new XMLWriter(System.out, format);
			console.write(document);

			XMLWriter filewriter = new XMLWriter(new FileOutputStream(new File("sample.xml")), format);
			filewriter.write(document);
			filewriter.flush(); // must have this line
		} catch (UnsupportedEncodingException | FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
}
