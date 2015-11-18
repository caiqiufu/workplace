package com.unieap.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author caibo
 * 
 */
public final class DOMUtils {
	protected static final Log log = LogFactory.getLog(DOMUtils.class);

	/**
	 * @return Document
	 * @throws Exception
	 */
	public static Document createNewDocument() throws Exception {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			log.error("创建Document异常,错误信息:" + e.getMessage());
			throw new Exception("创建Document异常,错误信息:" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * <p>
	 * 描述:默认是UTF-8字符集
	 * </P>
	 * Jan 31, 2011
	 * 
	 * @param str
	 * @return Document
	 * @throws Exception
	 */
	public static Document loadDocumentFromStr(String str) throws Exception {
		Document document = null;
		try {
			InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(is));
		} catch (Exception e) {
			log.error("解析XML异常,错误信息:" + e.getMessage());
			throw new Exception("解析XML异常,错误信息:" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * @param str
	 * @param charsetName
	 * @return Document
	 * @throws Exception
	 */
	public static Document loadDocumentFromStr(String str, String charsetName) throws Exception {
		Document document = null;
		if (str == null || str.indexOf(">") < 0)
			return null;
		try {
			InputStream is = new ByteArrayInputStream(str.getBytes(charsetName));
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(is));
		} catch (Exception exception) {
			log.error("解析XML异常,错误信息:" + exception.getMessage());
			throw new Exception("解析XML异常,错误信息:" + exception.getMessage(), exception);
		}
		return document;
	}

	/**
	 * @param f
	 * @return Document
	 * @throws Exception
	 */
	public static Document loadDocumentFromFile(File f) throws Exception {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (Exception e) {
			log.error("解析XML文件异常,错误信息:" + e.getMessage());
			throw new Exception("解析XML文件异常,错误信息:" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * @param f
	 * @return Document
	 * @throws Exception
	 */
	public static Document loadDocumentFromFile(InputStream f) throws Exception {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (Exception e) {
			log.error("解析XML文件异常,错误信息:" + e.getMessage());
			throw new Exception("解析XML文件异常,错误信息:" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * @param is
	 * @return Document
	 * @throws Exception
	 */
	public static Document loadDocumentFromInputStream(InputStream is) throws Exception {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		} catch (Exception e) {
			log.error("解析XML文件异常,错误信息:" + e.getMessage());
			throw new Exception("解析XML文件异常,错误信息:" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * @param uri
	 * @return Document
	 * @throws Exception
	 */
	public static Document loadDocumentFromUri(String uri) throws Exception {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
		} catch (Exception e) {
			log.error("解析XML文件异常,错误信息:" + e.getMessage());
			throw new Exception("解析XML文件异常,错误信息:" + e.getMessage(), e);
		}
		return document;
	}

	/**
	 * @param node
	 * @param subNode
	 */
	public static void appendChild(Node node, Node subNode) {
		node.appendChild(subNode);
	}

	/**
	 * @param node
	 * @param tagName
	 * @return boolean
	 */
	public static boolean containsNode(Node node, String tagName) {
		return getSingleNode(node, tagName) != null;
	}

	/**
	 * @param node
	 * @param tagName
	 * @return Node
	 */
	public static Node getSingleNode(Node node, String tagName) {
		if (node == null || tagName == null)
			return null;
		Node singleNode = null;
		NodeList nodeList = null;
		if (node instanceof Document)
			nodeList = ((Document) node).getElementsByTagName(tagName);
		else
			nodeList = ((Element) node).getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0)
			singleNode = nodeList.item(0);
		return singleNode;
	}

	/**
	 * @param node
	 * @param tagName
	 * @return Element
	 */
	public static Element getSingleElement(Node node, String tagName) {
		Element singleElement = null;
		if (containsNode(node, tagName))
			singleElement = (Element) getSingleNode(node, tagName);
		return singleElement;
	}

	/**
	 * @param node
	 * @param tagName
	 * @return NodeList
	 */
	public static NodeList getMultiNodes(Node node, String tagName) {
		if (node == null || tagName == null)
			return null;
		NodeList nodeList = null;
		if (node instanceof Document)
			nodeList = ((Document) node).getElementsByTagName(tagName);
		else
			nodeList = ((Element) node).getElementsByTagName(tagName);
		return nodeList;
	}

	/**
	 * @param node
	 * @param tagName
	 * @param offset
	 * @return Node
	 */
	public static Node getNode(Node node, String tagName, int offset) {
		if (node == null || tagName == null || offset < 0)
			return null;
		Node result = null;
		NodeList nodeList = getMultiNodes(node, tagName);
		if (offset < nodeList.getLength())
			result = nodeList.item(offset);
		return result;
	}

	/**
	 * @param node
	 * @param tagName
	 * @param offset
	 * @return Element
	 */
	public static Element getElement(Node node, String tagName, int offset) {
		Node result = getNode(node, tagName, offset);
		if (result == null)
			return null;
		else
			return (Element) result;
	}

	/**
	 * @param node
	 * @return String
	 */
	public static String getNodeValue(Node node) {
		if (node == null || (node instanceof Document))
			return null;
		NodeList nodelist = node.getChildNodes();
		if (nodelist.getLength() <= 0)
			return null;
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node eachNode = nodelist.item(i);
			if (eachNode.getNodeType() == 3)
				stringBuffer.append(eachNode.getNodeValue());
		}

		return stringBuffer.toString();
	}

	/**
	 * @param node
	 * @param tagName
	 * @return String
	 */
	public static String getSingleNodeValue(Node node, String tagName) {
		Node singleNode = getSingleNode(node, tagName);
		return getNodeValue(singleNode);
	}

	/**
	 * @param node
	 * @param tagName
	 * @return String[]
	 */
	public static String[] getMultiNodeValues(Node node, String tagName) {
		NodeList nodeList = getMultiNodes(node, tagName);
		if (nodeList == null)
			return new String[0];
		String nodeValues[] = new String[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++)
			nodeValues[i] = getNodeValue(nodeList.item(i));

		return nodeValues;
	}

	/**
	 * @param element
	 * @param attrName
	 * @return String
	 */
	public static String getAttributeValue(Element element, String attrName) {
		if (element == null || attrName == null)
			return null;
		else
			return element.getAttribute(attrName);
	}

	/**
	 * @param element
	 * @return HashMap
	 */
	public static Map<String, String> getAttributeValues(Element element) {
		Map<String, String> attributes = new HashMap<String, String>(3);
		NamedNodeMap map = element.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Attr attr = (Attr) map.item(i);
			attributes.put(attr.getName(), attr.getValue());
		}

		return attributes;
	}

	/**
	 * @param node
	 * @param tagName
	 * @param attrName
	 * @return String
	 */
	public static String getSingleAttributeValue(Node node, String tagName, String attrName) {
		String attributeValue = null;
		Element element = getSingleElement(node, tagName);
		if (element != null)
			attributeValue = element.getAttribute(attrName);
		return attributeValue;
	}

	/**
	 * @param node
	 * @param tagName
	 * @param attrName
	 * @return String[]
	 */
	public static String[] getMultiAttributeValue(Node node, String tagName, String attrName) {
		NodeList nodeList = getMultiNodes(node, tagName);
		if (nodeList == null)
			return new String[0];
		String attributeValues[] = new String[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++)
			attributeValues[i] = getAttributeValue((Element) nodeList.item(i), attrName);

		return attributeValues;
	}

	/**
	 * @param document
	 * @param elementName
	 * @param elementValue
	 * @return Element
	 */
	public static Element createElement(Document document, String elementName, String elementValue) {
		if (document == null || elementName == null) {
			return null;
		} else {
			Element element = document.createElement(elementName);
			setNodeValue(element, elementValue);
			return element;
		}
	}

	/**
	 * @param document
	 * @param elementName
	 * @param elementValue
	 * @return Element
	 */
	public static Element createAndAppendRoot(Document document, String elementName, String elementValue) {
		if (document == null || elementName == null) {
			return null;
		} else {
			Element element = document.createElement(elementName);
			document.appendChild(element);
			setNodeValue(element, elementValue);
			return element;
		}
	}

	/**
	 * @param node
	 * @param elementName
	 * @param elementValue
	 * @return Element
	 */
	public static Element createAndAppendElement(Node node, String elementName, String elementValue) {
		if (node == null || elementName == null)
			return null;
		Document document = null;
		if (node instanceof Document) {
			document = (Document) node;
		} else {
			document = node.getOwnerDocument();
		}
		Element element = document.createElement(elementName);
		node.appendChild(element);
		setNodeValue(element, elementValue);
		return element;
	}

	/**
	 * @param node
	 * @param elementName
	 * @param elementValues
	 */
	public static void createAndAppendMultiElement(Node node, String elementName, String elementValues[]) {
		if (node == null || elementName == null || elementValues == null || elementValues.length == 0
				|| (node instanceof Document))
			return;
		Document document = node.getOwnerDocument();
		for (int i = 0; i < elementValues.length; i++) {
			Element eachElement = document.createElement(elementName);
			node.appendChild(eachElement);
			setNodeValue(eachElement, elementValues[i]);
		}

	}

	/**
	 * @param node
	 * @param nodeValue
	 */
	public static void setNodeValue(Node node, String nodeValue) {
		if (node == null || (node instanceof Document))
			return;
		Document document = node.getOwnerDocument();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node eachNode = nodeList.item(i);
			if (eachNode.getNodeType() == 3)
				node.removeChild(eachNode);
		}

		if (nodeValue != null)
			node.appendChild(document.createTextNode(nodeValue));
	}

	/**
	 * @param node
	 * @param tagName
	 * @param nodeValue
	 */
	public static void setSingleNodeValue(Node node, String tagName, String nodeValue) {
		Node singleNode = getSingleNode(node, tagName);
		if (singleNode != null)
			setNodeValue(singleNode, nodeValue);
	}

	/**
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 */
	public static void setAttribute(Element element, String attributeName, String attributeValue) {
		if (element != null && attributeName != null)
			element.setAttribute(attributeName, attributeValue);
	}

	/**
	 * @param element
	 * @param attributes
	 */
	public static void setAttributes(Element element, Map<?, ?> attributes) {
		if (element == null || attributes == null) {
			return;
		}
		String key = null;
		Iterator<?> e = attributes.keySet().iterator();
		while (e.hasNext()) {
			element.setAttribute(key, attributes.get(key).toString());
			key = e.next().toString();
		}

	}

	/**
	 * @param node
	 * @param tagName
	 * @param attributeName
	 * @param attributeValue
	 */
	public static void setSingleNodeAttribute(Node node, String tagName, String attributeName, String attributeValue) {
		Element element = getSingleElement(node, tagName);
		setAttribute(element, attributeName, attributeValue);
	}

	/**
	 * XML org.w3c.dom.Document 转 String
	 * 
	 * @param doc
	 * @param code
	 * @return String
	 * @throws Exception
	 */
	/*public static String docToString(Document doc, String code) throws Exception {
		if (StringUtils.isEmpty(code)) {
			code = "UTF-8";
		}
		// XML转字符串
		String xmlStr = "";
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(bos));
			xmlStr = bos.toString();
			xmlStr = new String(xmlStr.getBytes("ISO8859_1"), code);
		} catch (Exception e) {
			log.error("Document传换成String异常,异常信息:" + e.getMessage());
			throw new Exception("Document传换成String异常,异常信息:" + e.getMessage(), e);
		}
		xmlStr = formatXml(xmlStr);
		return xmlStr;
	}*/

	/**
	 * <p>
	 * 描述:格式化xml文件
	 * </P>
	 * Jan 20, 2011
	 * 
	 * @param str
	 * @return String
	 * @throws Exception
	 */
	/*public static String formatXml(String str) throws Exception {
		org.dom4j.Document document = null;
		document = org.dom4j.DocumentHelper.parseText(str);
		// 格式化输出格式
		org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat.createPrettyPrint();
		// format.setEncoding("gb2312");
		StringWriter writer = new StringWriter();
		// 格式化输出流
		org.dom4j.io.XMLWriter xmlWriter = new org.dom4j.io.XMLWriter(writer, format);
		// 将document写入到输出流
		xmlWriter.write(document);
		xmlWriter.close();
		// 输出到控制台
		return writer.toString();
	}*/
}