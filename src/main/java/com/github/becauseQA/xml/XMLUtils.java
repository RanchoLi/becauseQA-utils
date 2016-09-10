package com.github.becauseQA.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.xpath.DefaultXPath;
import org.xml.sax.InputSource;

/**
 * marshaller xml or unmarshaller xml 
 * http://blog.bdoughan.com/2011/08/jaxb-and-java-io-files-streams-readers.html
 * @author Administrator
 *
 */
public class XMLUtils {

	public static Document document = null;
	private static File xmlFile = null;

	public static void readString(String content) {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
			read(byteArrayInputStream);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param url
	 *            The URL object
	 */
	public static void read(File file) {
		// TODO Auto-generated constructor stub
		try {
			xmlFile = file;
			document = new SAXReader().read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param url
	 *            The URL object
	 */
	public static void read(URL url) {
		// TODO Auto-generated constructor stub
		String systemID = url.toExternalForm();
		read(systemID);
	}

	/**
	 * @param url
	 *            The string url
	 */
	public static void read(String url) {
		// TODO Auto-generated constructor stub
		InputSource source = new InputSource(url);
		source.setEncoding("UTF-8");
		read(source);

	}

	/**
	 * @param inputStream
	 *            The inputstream object
	 */
	public static void read(InputStream inputStream) {
		// TODO Auto-generated constructor stub
		InputSource source = new InputSource(inputStream);
		source.setEncoding("UTF-8");
		read(source);

	}

	/**
	 * @param reader
	 *            The inputstream object
	 */
	public static void read(Reader reader) {
		// TODO Auto-generated constructor stub
		InputSource source = new InputSource(reader);
		source.setEncoding("UTF-8");
		read(source);

	}

	/**
	 * @param inputSource
	 *            the xml object 1. if it's a xml file : new
	 *            ByteArrayInputStream(xmlfile.getBytes("UTF-8")) 2. if it's
	 *            inputstream: just past it;
	 * @return
	 */
	public static void read(InputSource inputSource) {
		try {
			document = new SAXReader().read(inputSource);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/**
	 * @param xpath
	 *            xpath could be //ns:node1/ns://node2 it's from namespace xpath
	 *            xpath could be //ns:ListBucketResult/ns:CommonPrefixes[last()-1]/ns:Prefix
	 *            xpath could be //*[local-name()='nodename' and
	 *            text()='nodetext']
	 * @return Node the node object
	 */
	public static Node getXPathNode(String xpath) {
		Node node = null;
		if (xpath.contains("ns")) {// here had used the namespace path
			DefaultXPath defaultXPath = new DefaultXPath(xpath);

			Map<String, String> namespaces = new TreeMap<String, String>();
			Element rootElement = document.getRootElement();
			String namespace = rootElement.getNamespaceURI();
			namespaces.put("ns", namespace);

			defaultXPath.setNamespaceURIs(namespaces);
			node = defaultXPath.selectSingleNode(document);

		}else{
			node = document.selectSingleNode(xpath);
		}
		
		return node;
	}

	public static String getNodeAttributeValue(String xpath, String attributeName) {
		Node xPathNode = getXPathNode(xpath);
		return xPathNode.valueOf("@" + attributeName);
	}

	/**
	 * @param xpath
	 *            xpath could be //ns:node1/ns://node2 it's from namespace xpath
	 *            xpath could be //*[local-name()='nodename' and
	 *            text()='nodetext']
	 * @return Node the node object
	 */
	@SuppressWarnings("unchecked")
	public static List<Node> getXPathNodes(String xpath) {
		List<Node> node = null;
		if (xpath.contains("ns")) {// here had used the namespace path
			DefaultXPath defaultXPath = new DefaultXPath(xpath);

			Map<String, String> namespaces = new TreeMap<String, String>();
			Element rootElement = document.getRootElement();
			String namespace = rootElement.getNamespaceURI();
			namespaces.put("ns", namespace);

			defaultXPath.setNamespaceURIs(namespaces);
			node = defaultXPath.selectNodes(document);

		}else{
			node = document.selectNodes(xpath);
		}
		
		return node;
	}

	/**
	 * @param nodeName
	 *            node name
	 * @param nodeValue
	 *            the node value need to add
	 */
	public static void addNode(String nodeName, String nodeValue) {
		Element rootElement = document.getRootElement();
		if (rootElement == null) {
			rootElement = DocumentHelper.createElement("sample");
			document.setRootElement(rootElement);
		}
		Element firstelement = rootElement.addElement(nodeName);
		firstelement.setText(nodeValue);
	}

	/**
	 * Save the xml document if we call the addNode method to add some xml nodes
	 */
	public static void saveDocument() {
		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");
		try {
			XMLWriter filewriter = new XMLWriter(new FileOutputStream(xmlFile), format);
			filewriter.write(document);
			filewriter.flush(); // must have this line
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*-----------------------------------------------------JDK XML PARSE------------------------------------------------------------------------------------------------------*/
	/**
	 * @param outputXmlFile
	 *            java xml entity from jaxb
	 * @param rootClass
	 *            the root xml object class
	 */
	public static void marshal(Object rootClass,File outputXmlFile) {
		try {
			JAXBContext jc = JAXBContext.newInstance(rootClass.getClass());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 设置输出编码,默认为UTF-8
			//marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", value);
			marshaller.marshal(rootClass, outputXmlFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param outputXmlFile
	 *            java xml entity from jaxb
	 * @param rootClass
	 *            the root xml object class
	 */
	public static String marshal(Object rootObject) {
		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext jc = JAXBContext.newInstance(rootObject.getClass());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //格式化输出
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 设置输出编码,默认为UTF-8
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.example.com/address address.xsd");
			//marshaller.setProperty("eclipselink.media.type", "application/json");
			marshaller.marshal(rootObject, stringWriter);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	/**
	 * @param outputXmlFile
	 *            java xml entity from jaxb
	 * @param rootClass
	 *            the root xml object class
	 */
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(File outputXmlFile, Class... rootClass) {
		Object object = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(rootClass);
			Unmarshaller marshaller = jc.createUnmarshaller();
			//The JAXB specification does not define any Unmarshaller properties, however individual implementations may.  
			//EclipseLink JAXB (MOXy) for example offers a property that enables JSON binding.
			//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 设置输出编码,默认为UTF-8
			object = marshaller.unmarshal(outputXmlFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * @param xmlContent
	 *            java xml entity from jaxb
	 * @param rootClass
	 *            the root xml object class
	 */
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(String xmlContent, Class... rootClass) {
		Object object = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(rootClass);
			Unmarshaller marshaller = jc.createUnmarshaller();
			String bufferString = new StringBuffer(xmlContent).toString();
			StreamSource streamSource = new StreamSource(new StringReader(bufferString));
			object = marshaller.unmarshal(streamSource);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * @param url
	 *            java xml entity from jaxb
	 * @param rootClass
	 *            the root xml object class
	 */
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(URL url, Class... rootClass) {
		Object object = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(rootClass);
			Unmarshaller marshaller = jc.createUnmarshaller();
			object = marshaller.unmarshal(url);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * @param inputStream
	 *            java xml entity from jaxb
	 * @param rootClass
	 *            the root xml object class
	 */
	@SuppressWarnings("rawtypes")
	public static Object unmarshal(InputStream inputStream, Class... rootClass) {
		Object object = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(rootClass);
			Unmarshaller marshaller = jc.createUnmarshaller();
			object = marshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

}
