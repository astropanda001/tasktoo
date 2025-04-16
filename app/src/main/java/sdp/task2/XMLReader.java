import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;

public class XMLReader {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("data.xml"); // make sure this path is correct

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("record");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println("Name: " + getTagValue("name", element));
                    System.out.println("Postal Zip: " + getTagValue("postalZip", element));
                    System.out.println("Region: " + getTagValue("region", element));
                    System.out.println("Country: " + getTagValue("country", element));
                    System.out.println("Address: " + getTagValue("address", element));
                    System.out.println("List: " + getTagValue("list", element));
                    System.out.println("--------------------------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (nodeList != null && nodeList.getLength() > 0) ? nodeList.item(0) : null;
        return (node != null) ? node.getNodeValue() : "";
    }
}
