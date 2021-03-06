package lwg.nwpu.ides.Util;

import java.util.HashMap;
import java.util.Map;

public class Element {
	// XML文件元素的XPath
	private String XPath;

	// 节点名称
	private String name;
	
	// 属性
	private Map<String, XMLAttribute> attributes = new HashMap<>();

	

	public Map<String, XMLAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, XMLAttribute> attribute) {
		this.attributes = attribute;
	}

	
	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public XMLAttribute getAttribute(String attr) {
		return attributes.get(attr);
	}

	public void addAttribute(String attribute, XMLAttribute value) {
		this.attributes.put(attribute, value);
	}

	public String getXPath() {
		return XPath;
	}

	public void setXPath(String xPath) {
		XPath = xPath;
	}
}
