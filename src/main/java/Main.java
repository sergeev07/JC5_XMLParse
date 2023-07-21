import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileName = "data.xml";
        String outputFileName = "data.json";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json, outputFileName);

    }


    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<Employee> employees = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));

        NodeList nodeList = document.getDocumentElement().getElementsByTagName("employee");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node employee = nodeList.item(i);
            Element nElement = (Element) employee;

            employees.add(new Employee(Long.parseLong(nElement.getElementsByTagName("id").item(0).getTextContent()),
                    nElement.getElementsByTagName("firstName").item(0).getTextContent(),
                    nElement.getElementsByTagName("lastName").item(0).getTextContent(),
                    nElement.getElementsByTagName("country").item(0).getTextContent(),
                    Integer.parseInt(nElement.getElementsByTagName("age").item(0).getTextContent())));
        }
        return employees;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    static void writeString(String json, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
