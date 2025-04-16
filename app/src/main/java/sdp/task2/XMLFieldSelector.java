import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.InputStream;
import java.util.*;

public class XMLFieldSelector {

    public static void main(String[] args) {
        try {
            // Ask user which fields to print
            Scanner scanner = new Scanner(System.in);
            System.out.println("Available fields: name, postalZip, region, country, address, list");
            System.out.print("Enter fields to display (comma-separated): ");
            String input = scanner.nextLine();

            Set<String> selectedFields = new HashSet<>(Arrays.asList(input.trim().split("\\s*,\\s*")));

            // Load the XML file from resources
            InputStream inputStream = XMLReader.class.getClassLoader().getResourceAsStream("data.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("record");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    for (String field : selectedFields) {
                        String value = getTagValue(field, element);
                        System.out.println(capitalize(field) + ": " + value);
                    }
                    System.out.println("--------------------------------------");
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) return "[Not Found]";
        Node node = nodeList.item(0).getFirstChild();
        return (node != null) ? node.getNodeValue() : "";
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
