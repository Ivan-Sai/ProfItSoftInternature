package org.example.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

public class XmlWriteService {
    public void writeMapToXml(Map<String, Integer> statistics, String arg) {

        String filePath = String.format("./src/main/resources/statistics_by_%s.xml", arg);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element statisticsElement = document.createElement("statistics");
            document.appendChild(statisticsElement);

            for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
                Element itemElement = document.createElement("item");

                Element valueElement = document.createElement("value");
                valueElement.appendChild(document.createTextNode(entry.getKey()));
                itemElement.appendChild(valueElement);

                Element countElement = document.createElement("count");
                countElement.appendChild(document.createTextNode(entry.getValue().toString()));
                itemElement.appendChild(countElement);

                statisticsElement.appendChild(itemElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);

            System.out.println("XML файл було успішно створено: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
