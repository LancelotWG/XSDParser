package lwg.nwpu.ides.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XSDElement {
	// 节点名称
	private String name;

	// 节点描述
	private XSDAnnotation annotation = new XSDAnnotation();
	
	// 属性
	private Map<String, XSDAttribute> attribute = new HashMap<>();

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
		return attribute.get(attr);
	}

	public void addAttribute(String attribute, XSDAttribute value) {
		this.attribute.put(attribute, value);
	}

	public XSDElement getElements(String name) {
		return elements.get(name);
	}

	public void addElements(String name,XSDElement element) {
		this.elements.put(name, element);
	}
}
