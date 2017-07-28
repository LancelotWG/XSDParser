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

public class XMLReader {
	
	private static final String JSON_FITERINGSELECT_STR_1 = "{identifier:\"id\",label: \"name\", items:";// ����fitleringSelect�ؼ���Ҫ���ַ���
	private static final String JSON_FITERINGSELECT_STR_2 = "}";// ����fitleringSelect�ؼ���Ҫ���ַ������������ط���������ʹ��ûʲô����
																// �����������õ��ˣ���Ϊ����������Ŀ�Ķ��������Բ���̫����

	public void parserXML(String XMLPath) throws Exception {
		// ����SAXReader����
		SAXReader reader = new SAXReader();
		// ��ȡ�ļ� ת����Document
		Document document = reader.read(new File(XMLPath));
		// ��ȡ���ڵ�Ԫ�ض���
		Element root = document.getRootElement();
		// ����
		listNodes(root);
	}

	// ������ǰ�ڵ��µ����нڵ�
	public void listNodes(Element node) {
		System.out.println("��ǰ�ڵ�����ƣ�" + node.getName());
		// ���Ȼ�ȡ��ǰ�ڵ���������Խڵ�
		List<Attribute> list = node.attributes();
		// �������Խڵ�
		for (Attribute attribute : list) {
			System.out.println("����" + attribute.getName() + ":" + attribute.getValue());
		}
		// �����ǰ�ڵ����ݲ�Ϊ�գ������
		if (!(node.getTextTrim().equals(""))) {
			System.out.println(node.getName() + "��" + node.getText());
		}
		// ͬʱ������ǰ�ڵ�����������ӽڵ�
		// ʹ�õݹ�
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
			//Map<String, XSDElement> nodes = xsdReader.paserXSD("RmConfig.cxsd");
			//JSONArray jsonArray = JSONArray.fromObject(nodes);
			
			//String json = jsonArray.toString();
			
			//System.out.println(json);
			
			xmlReader.showJson();
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