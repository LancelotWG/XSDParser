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
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class XSDReader {
	private List<XSDNode> list = new ArrayList<XSDNode>();

	/**
	 * 
	 * ����XSD���������ݽڵ�����б�
	 * 
	 * 
	 * 
	 * @param xsd
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 */

	public List<XSDNode> paserXSD(String xsd) throws Exception {

		SAXReader saxReader = new SAXReader();

		// ByteArrayInputStream byteArrayInputStream = new
		// ByteArrayInputStream(xsd.getBytes(BaseConstants.XM LENCODING));
		Map map = new HashMap();  
        map.put("xs",XMLConstants.NAMESPACEADDRESS);         
        File file = new File(xsd);  
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map); 
		Document doc = saxReader.read(file);
        
		Element element = doc.getRootElement();

		String basePath = null;
		Element dataElement = null;
		String elementPath = null;
		
		elementPath = "//" + getXSDDefaultNamespace() + "element";
		
		if ("".equals(XMLConstants.MESSAGE)) {
				dataElement = element;
				List<Element> elementNodes = element.elements("element");
				for (Iterator<Element> iterator = elementNodes.iterator(); iterator.hasNext();) {
					Element element2 = (Element) iterator.next();
					paseData(element2, "//", elementPath, "//");
				}
			} else {
				basePath = "//" + getXSDDefaultNamespace() + "element[@name=\"" + XMLConstants.MESSAGE + "\"]";
				dataElement = (Element) element.selectSingleNode(basePath);
				paseData(dataElement, "//", elementPath, "//");
			}
		
		return list;

	}

	/**
	 * 
	 * ת��XSD�����ݽڵ㣬����XSDNode����
	 * 
	 * 
	 * 
	 * @param element
	 * 
	 * @param xPath
	 * 
	 * @param xsdPath
	 * 
	 * @param unboundedXpath
	 * 
	 */

	private String getXSDDefaultNamespace() {
		if ("".equals(XMLConstants.XSD_DEFAULT_NAMESPACE)) {
			return "";
		}else{
			return XMLConstants.XSD_DEFAULT_NAMESPACE + ":";
		}
	}
	
	public void paseData(Element element, String xPath, String xsdPath, String unboundedXpath) {

		if (element == null)
			return;

		// ��ȡ�ڵ�name����
		String nodeName = element.attributeValue("name");

		// ��װxml�ĵ��нڵ��XPath

		xPath += nodeName;

		unboundedXpath += nodeName;

		// ���ж�ڵ���������
		String maxOccurs = element.attributeValue("maxOccurs");

		if (maxOccurs != null && !"1".equals(maxOccurs) && !("//" + XMLConstants.MESSAGE + "").equals(xPath)) {// �ڵ�����ж��

			unboundedXpath += XMLConstants.XSD_UNBOUNDED;

		}

		// ��װ��һ��elementԪ�ص�XPath

		String currentXsdPath = xsdPath + "[@name=\"" + nodeName + "\"]" + "/" + getXSDDefaultNamespace()

				+ "complexType/" + getXSDDefaultNamespace() + "sequence/"
				+ getXSDDefaultNamespace()

				+ "element";

		// ���Ҹýڵ������е�elementԪ��

		List<Node> elementNodes = element.selectNodes(currentXsdPath);

		if (elementNodes != null && elementNodes.size() > 0) {// ������滹��element,˵������Ҷ��

			Iterator<Node> nodes = elementNodes.iterator();

			while (nodes.hasNext()) {

				if (!xPath.endsWith("/")) {

					xPath += "/";

					unboundedXpath += "/";

				}

				Element ele = (Element) nodes.next();

				paseData(ele, xPath, currentXsdPath, unboundedXpath);

			}

		} else { // ��elementΪҶ��

			XSDNode xsdNode = new XSDNode();

			// ��ȡע�ͽڵ�

			String annotation = "";

			Node annotationNode = element

					.selectSingleNode(xsdPath + "[@name=\"" + nodeName + "\"]/" + getXSDDefaultNamespace()

							+ "annotation/" + getXSDDefaultNamespace() + "documentation");

			if (annotationNode != null)

				annotation = annotationNode.getText();

			// ��ȡ�ڵ���������

			String nodeType = "";

			Attribute type = element.attribute("type");

			if (type != null) {

				nodeType = type.getText();

			} else {

				String spath = xsdPath + "[@name=\"" + nodeName + "\"]/" + getXSDDefaultNamespace()
						+ "simpleType/"

						+ getXSDDefaultNamespace() + "restriction";

				Element typeNode = (Element) element.selectSingleNode(spath);

				if (typeNode != null) {

					Attribute base = typeNode.attribute("base");

					if (base != null)

						nodeType = base.getText();

				}

			}

			xsdNode.setName(nodeName);

			xsdNode.setXPath(xPath);

			xsdNode.setAnnotation(annotation);

			xsdNode.setType(nodeType);

			xsdNode.setUnboundedXpath(unboundedXpath);

			list.add(xsdNode);

		}

	}

	public static void main(String[] args) {

		try {
			String realPath = XSDReader.class.getResource("/").getPath();
			XSDReader xsdReader = new XSDReader();

			List<XSDNode> nodes = xsdReader.paserXSD("RmConfig.cxsd");

			for (XSDNode node : nodes) {
				System.out.println(node.getUnboundedXpath());
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}
}
