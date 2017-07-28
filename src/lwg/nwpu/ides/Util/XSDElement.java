package lwg.nwpu.ides.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XSDElement {
	
	//XML文件元素的XPath
	private String XPath;
	
	// 节点名称
	private String name;

	// 节点描述
	private XSDAnnotation annotation = new XSDAnnotation();
	
	public Map<String, XSDAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, XSDAttribute> attribute) {
		this.attributes = attribute;
	}

	public Map<String, XSDElement> getElements() {
		return elements;
	}

	public void setElements(Map<String, XSDElement> elements) {
		this.elements = elements;
	}

	// 属性
	private Map<String, XSDAttribute> attributes = new HashMap<>();

	//子节点
	private Map<String, XSDElement> elements = new HashMap<>();
	
	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public XSDAnnotation getAnnotation() {

		return annotation;

	}

	public XSDAttribute getAttribute(String attr) {
		return attributes.get(attr);
	}

	public void addAttribute(String attribute, XSDAttribute value) {
		this.attributes.put(attribute, value);
	}

	public XSDElement getElement(String name) {
		return elements.get(name);
	}

	public void addElement(String name,XSDElement element) {
		this.elements.put(name, element);
	}

	public String getXPath() {
		return XPath;
	}

	public void setXPath(String xPath) {
		XPath = xPath;
	}
	
	
}
