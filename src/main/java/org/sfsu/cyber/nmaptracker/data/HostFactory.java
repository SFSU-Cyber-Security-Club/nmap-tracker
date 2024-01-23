package org.sfsu.cyber.nmaptracker.data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class HostFactory {

    public static Host createHostFromXml(String xml) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        Element hostElement = document.getDocumentElement();
        Host host = new Host();

        // Set starttime and endtime
        host.setStarttime(Long.parseLong(hostElement.getAttribute("starttime")));
        host.setEndtime(Long.parseLong(hostElement.getAttribute("endtime")));

        // Parse and set status
        NodeList statusList = hostElement.getElementsByTagName("status");
        if (statusList.getLength() > 0) {
            Element statusElement = (Element) statusList.item(0);
            Host.Status status = new Host.Status();
            status.setState(statusElement.getAttribute("state"));
            status.setReason(statusElement.getAttribute("reason"));
            status.setReasonTtl(Integer.parseInt(statusElement.getAttribute("reason_ttl")));
            host.setStatus(status);
        }

        // Parse and set address
        NodeList addressList = hostElement.getElementsByTagName("address");
        if (addressList.getLength() > 0) {
            Element addressElement = (Element) addressList.item(0);
            Host.Address address = new Host.Address();
            address.setAddr(addressElement.getAttribute("addr"));
            address.setAddrtype(addressElement.getAttribute("addrtype"));
            host.setAddress(address);
        }

        // Parse and set ports
        NodeList portsList = hostElement.getElementsByTagName("ports");
        if (portsList.getLength() > 0) {
            Element portsElement = (Element) portsList.item(0);
            NodeList portList = portsElement.getElementsByTagName("port");
            List<Port> ports = new ArrayList<>();
            for (int i = 0; i < portList.getLength(); i++) {
                Element portElement = (Element) portList.item(i);
                ports.add(PortFactory.createPortFromXml(toString(portElement)));
            }
            host.setPorts(ports);
        }

        // Parse and set times
        NodeList timesList = hostElement.getElementsByTagName("times");
        if (timesList.getLength() > 0) {
            Element timesElement = (Element) timesList.item(0);
            Host.Times times = new Host.Times();
            times.setSrtt(Integer.parseInt(timesElement.getAttribute("srtt")));
            times.setRttvar(Integer.parseInt(timesElement.getAttribute("rttvar")));
            times.setTo(Integer.parseInt(timesElement.getAttribute("to")));
            host.setTimes(times);
        }

        return host;
    }

    // Utility method to convert a DOM Element back to a String representation
    private static String toString(Element element) throws IOException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
        return writer.toString();
    }
}

