package lwg.nwpu.ides.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class XMLReader {
	
	private static final String JSON_FITERINGSELECT_STR_1 = "{identifier:\"id\",label: \"name\", items:";// 构造fitleringSelect控件需要的字符串
	private static final String JSON_FITERINGSELECT_STR_2 = "}";// 构造fitleringSelect控件需要的字符串，这两个地方在这里面使用没什么意义
																// ，尽管下面用到了，因为是我其他项目的东西，所以不用太纠结

	public void parserXML(String XMLPath) throws Exception {
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
		// 读取文件 转换成Document
		Document document = reader.read(new File(XMLPath));
		// 获取根节点元素对象
		Element root = document.getRootElement();
		// 遍历
		listNodes(root);
	}

	// 遍历当前节点下的所有节点
	public void listNodes(Element node) {
		System.out.println("当前节点的名称：" + node.getName());
		// 首先获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attribute : list) {
			System.out.println("属性" + attribute.getName() + ":" + attribute.getValue());
		}
		// 如果当前节点内容不为空，则输出
		if (!(node.getTextTrim().equals(""))) {
			System.out.println(node.getName() + "：" + node.getText());
		}
		// 同时迭代当前节点下面的所有子节点
		// 使用递归
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String realPath = XSDReader.class.getResource("/").getPath();
			XMLReader xmlReader = new XMLReader();
			XSDReader xsdReader = new XSDReader();
			Map<String, XSDElement> nodes = xsdReader.paserXSD("RmConfig.cxsd");
			JSONObject jsonObject = JSONObject.fromObject(nodes);
			
			JSONArray jsonArray = JSONArray.fromObject(nodes);
			
			String json = jsonArray.toString();
			
			//System.out.println(json);
			System.out.println(jsonObject);
			
			//xmlReader.showJson();
			int a = 1;
		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	public String showJson() {
		List jsonList = new ArrayList();
		List rootList = new ArrayList();
		Map rootMap = new HashMap<String, Object>();
		rootMap.put("type", "root");
		rootMap.put("id", "objectId");
		rootMap.put("name", "A");
		String childStr = "[{_reference:1}]";
		rootMap.put("children", childStr);
		rootList.add(rootMap);

		Map rootMap1 = new HashMap<String, Object>();
		rootMap1.put("parentId", "");
		rootMap1.put("type", "child");
		rootMap1.put("name", "b-1");
		rootMap1.put("id", "2");
		jsonList.add(rootMap1);

		Map rootMap2 = new HashMap<String, Object>();
		rootMap2.put("parentId", "1");
		rootMap2.put("type", "child");
		rootMap2.put("name", "b-2");
		rootMap2.put("id", "7");
		jsonList.add(rootMap2);
		Map rootMap3 = new HashMap<String, Object>();
		rootMap3.put("parentId", "1");
		rootMap3.put("type", "child");
		rootMap3.put("name", "b-3");
		rootMap3.put("id", "4");
		jsonList.add(rootMap3);
		Map rootMap4 = new HashMap<String, Object>();
		rootMap4.put("parentId", "361");
		rootMap4.put("type", "child");
		rootMap4.put("name", "b-4");
		rootMap4.put("id", "6");
		jsonList.add(rootMap4);

		Map rootMap8 = new HashMap<String, Object>();
		rootMap8.put("parentId", "0");
		rootMap8.put("type", "parent");
		rootMap8.put("ispage", "0");
		rootMap8.put("name", "B");

		rootMap8.put("id", "1");
		String childStr1 = "[{_reference:2},{_reference:7},{_reference:4},{_reference:6}]";
		rootMap8.put("children", childStr1);
		jsonList.add(rootMap8);
		JSONArray jsonArray = JSONArray.fromObject(jsonList);
		JSONArray rootJsonArray = JSONArray.fromObject(rootList);
		String json = jsonArray.toString();
		String rootJson = rootJsonArray.toString();
		try {
			json = json.replace("\"\\[", "\\[");
			json = json.replace("\\]\"", "\\]");
			rootJson = rootJson.replace("\"\\[", "\\[");
			rootJson = rootJson.replace("\\]\"", "\\]");
			if (!"[]".equals(json)) {
				json = json.substring(0, json.length() - 1);
				json = this.JSON_FITERINGSELECT_STR_1 + json;
				rootJson = rootJson.substring(1, rootJson.length() - 1);
				json = json + "," + rootJson;
			} else {
				json = this.JSON_FITERINGSELECT_STR_1 + "[";
				rootJson = rootJson.substring(1, rootJson.length() - 1);
				json = json + rootJson;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		json = json + "]" + this.JSON_FITERINGSELECT_STR_2;
		try {
			json = json.replaceAll("\"\\[", "\\[");
			json = json.replaceAll("\\]\"", "\\]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("json========" + json);
		return json;
	}
		 
}
