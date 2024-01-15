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

    // public static void main(String[] args) {
    //     String xml = "<host starttime=\"1705043335\" endtime=\"1705043335\">\r\n" + //
    //             "    <status state=\"up\" reason=\"conn-refused\" reason_ttl=\"0\"/>\r\n" + //
    //             "    <address addr=\"192.168.56.1\" addrtype=\"ipv4\"/>\r\n" + //
    //             "    <hostnames>\r\n" + //
    //             "    </hostnames>\r\n" + //
    //             "    <ports>\r\n" + //
    //             "        <port protocol=\"tcp\" portid=\"135\">\r\n" + //
    //             "            <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "            <service name=\"msrpc\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "        </port>\r\n" + //
    //             "        <port protocol=\"tcp\" portid=\"139\">\r\n" + //
    //             "            <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "            <service name=\"netbios-ssn\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "        </port>\r\n" + //
    //             "    </ports>\r\n" + //
    //             "    <times srtt=\"137\" rttvar=\"118\" to=\"100000\"/>\r\n" + //
    //             "</host>"; 

    //     try {
    //         Host host = createHostFromXml(xml);
    //         // Do something with the host object
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}

