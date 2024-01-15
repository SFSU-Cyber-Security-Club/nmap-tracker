package org.sfsu.cyber.nmaptracker.data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;

public class PortFactory {

    public static Port createPortFromXml(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        Element portElement = document.getDocumentElement();
        Port port = new Port();

        // Set protocol and portid
        port.setProtocol(portElement.getAttribute("protocol"));
        port.setPortid(Integer.parseInt(portElement.getAttribute("portid")));

        // Parse and set state
        NodeList stateNodes = portElement.getElementsByTagName("state");
        if (stateNodes.getLength() > 0) {
            Node stateNode = stateNodes.item(0);
            if (stateNode.getNodeType() == Node.ELEMENT_NODE) {
                Element stateElement = (Element) stateNode;
                Port.State state = new Port.State();
                state.setState(stateElement.getAttribute("state"));
                state.setReason(stateElement.getAttribute("reason"));
                state.setReasonTtl(Integer.parseInt(stateElement.getAttribute("reason_ttl")));
                port.setState(state);
            }
        }

        // Parse and set service
        NodeList serviceNodes = portElement.getElementsByTagName("service");
        if (serviceNodes.getLength() > 0) {
            Node serviceNode = serviceNodes.item(0);
            if (serviceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element serviceElement = (Element) serviceNode;
                Port.Service service = new Port.Service();
                service.setName(serviceElement.getAttribute("name"));
                service.setMethod(serviceElement.getAttribute("method"));
                service.setConf(Integer.parseInt(serviceElement.getAttribute("conf")));
                port.setService(service);
            }
        }

        return port;
    }

    // public static void main(String[] args) {
    //     String xml = "<port protocol=\"tcp\" portid=\"135\">\n" +
    //                  "    <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\n" +
    //                  "    <service name=\"msrpc\" method=\"table\" conf=\"3\"/>\n" +
    //                  "</port>";

    //     try {
    //         Port port = createPortFromXml(xml);
    //         // Do something with the port object
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
