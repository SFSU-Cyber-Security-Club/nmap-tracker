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

public class ScanFactory {

    public static Scan createScanFromXml(String xml) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        Element scanElement = document.getDocumentElement();
        Scan scan = new Scan();

        // Set attributes of Scan
        scan.setScanner(scanElement.getAttribute("scanner"));
        scan.setArgs(scanElement.getAttribute("args"));
        scan.setStart(Long.parseLong(scanElement.getAttribute("start")));
        scan.setStartstr(scanElement.getAttribute("startstr"));
        scan.setVersion(scanElement.getAttribute("version"));
        scan.setXmloutputversion(scanElement.getAttribute("xmloutputversion"));

        // Parse and set hosts
        NodeList hostList = scanElement.getElementsByTagName("host");
        List<Host> hosts = new ArrayList<>();
        for (int i = 0; i < hostList.getLength(); i++) {
            Element hostElement = (Element) hostList.item(i);
            hosts.add(HostFactory.createHostFromXml(toString(hostElement)));
        }
        scan.setHosts(hosts);

        // Parse and set runstats
        NodeList runstatsList = scanElement.getElementsByTagName("runstats");
        if (runstatsList.getLength() > 0) {
            Element runstatsElement = (Element) runstatsList.item(0);
            Scan.RunStats runstats = new Scan.RunStats();
            // Assuming finished and hosts elements are children of runstats
            Element finishedElement = (Element) runstatsElement.getElementsByTagName("finished").item(0);
            Element hostsElement = (Element) runstatsElement.getElementsByTagName("hosts").item(0);

            runstats.setTime(Long.parseLong(finishedElement.getAttribute("time")));
            runstats.setTimestr(finishedElement.getAttribute("timestr"));
            runstats.setSummary(finishedElement.getAttribute("summary"));
            runstats.setElapsed(Double.parseDouble(finishedElement.getAttribute("elapsed")));
            runstats.setExit(finishedElement.getAttribute("exit"));
            runstats.setUp(Integer.parseInt(hostsElement.getAttribute("up")));
            runstats.setDown(Integer.parseInt(hostsElement.getAttribute("down")));
            runstats.setTotal(Integer.parseInt(hostsElement.getAttribute("total")));

            scan.setRunstats(runstats);
        }

        return scan;
    }

    // Utility method to convert a DOM Node to a String representation
    // private static String toString(Node node) throws IOException {
    //     TransformerFactory transformerFactory = TransformerFactory.newInstance();
    //     Transformer transformer = transformerFactory.newTransformer();
    //     StringWriter writer = new StringWriter();
    //     transformer.transform(new DOMSource(node), new StreamResult(writer));
    //     return writer.toString();
    // }
    // Utility method to convert a DOM Element back to a String representation
    private static String toString(Element element) throws IOException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
        return writer.toString();
    }

    // public static void main(String[] args) {
    //     String xml = "<nmaprun scanner=\"nmap\" args=\"nmap -T4 -&#45;top-ports 50 -oX /media/sf_shared/nmapxml/subnet-top-50 192.168.56.1/24\" start=\"1705043325\" startstr=\"Thu Jan 11 23:08:45 2024\" version=\"7.93\" xmloutputversion=\"1.05\">\r\n" + //
    //             "    <scaninfo type=\"connect\" protocol=\"tcp\" numservices=\"50\" services=\"21-23,25-26,53,80-81,110-111,113,135,139,143,179,199,443,445,465,514-515,548,554,587,646,993,995,1025-1027,1433,1720,1723,2000-2001,3306,3389,5060,5666,5900,6001,8000,8008,8080,8443,8888,10000,32768,49152,49154\"/>\r\n" + //
    //             "    <verbose level=\"0\"/>\r\n" + //
    //             "    <debugging level=\"0\"/>\r\n" + //
    //             "    <hosthint>\r\n" + //
    //             "        <status state=\"up\" reason=\"unknown-response\" reason_ttl=\"0\"/>\r\n" + //
    //             "        <address addr=\"192.168.56.1\" addrtype=\"ipv4\"/>\r\n" + //
    //             "        <hostnames>\r\n" + //
    //             "        </hostnames>\r\n" + //
    //             "    </hosthint>\r\n" + //
    //             "    <hosthint>\r\n" + //
    //             "        <status state=\"up\" reason=\"unknown-response\" reason_ttl=\"0\"/>\r\n" + //
    //             "        <address addr=\"192.168.56.102\" addrtype=\"ipv4\"/>\r\n" + //
    //             "        <hostnames>\r\n" + //
    //             "        </hostnames>\r\n" + //
    //             "    </hosthint>\r\n" + //
    //             "    <host starttime=\"1705043335\" endtime=\"1705043335\">\r\n" + //
    //             "        <status state=\"up\" reason=\"conn-refused\" reason_ttl=\"0\"/>\r\n" + //
    //             "        <address addr=\"192.168.56.1\" addrtype=\"ipv4\"/>\r\n" + //
    //             "        <hostnames>\r\n" + //
    //             "        </hostnames>\r\n" + //
    //             "        <ports>\r\n" + //
    //             "            <extraports state=\"closed\" count=\"46\">\r\n" + //
    //             "                <extrareasons reason=\"conn-refused\" count=\"46\" proto=\"tcp\" ports=\"21-23,25-26,53,80-81,110-111,113,143,179,199,443,465,514-515,548,554,587,646,993,995,1025-1027,1433,1720,1723,2000-2001,3306,5060,5666,5900,6001,8000,8008,8080,8443,8888,10000,32768,49152,49154\"/>\r\n" + //
    //             "            </extraports>\r\n" + //
    //             "            <port protocol=\"tcp\" portid=\"135\">\r\n" + //
    //             "                <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "                <service name=\"msrpc\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "            </port>\r\n" + //
    //             "            <port protocol=\"tcp\" portid=\"139\">\r\n" + //
    //             "                <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "                <service name=\"netbios-ssn\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "            </port>\r\n" + //
    //             "            <port protocol=\"tcp\" portid=\"445\">\r\n" + //
    //             "                <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "                <service name=\"microsoft-ds\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "            </port>\r\n" + //
    //             "            <port protocol=\"tcp\" portid=\"3389\">\r\n" + //
    //             "                <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "                <service name=\"ms-wbt-server\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "            </port>\r\n" + //
    //             "        </ports>\r\n" + //
    //             "        <times srtt=\"137\" rttvar=\"118\" to=\"100000\"/>\r\n" + //
    //             "    </host>\r\n" + //
    //             "    <host starttime=\"1705043335\" endtime=\"1705043335\">\r\n" + //
    //             "        <status state=\"up\" reason=\"conn-refused\" reason_ttl=\"0\"/>\r\n" + //
    //             "        <address addr=\"192.168.56.102\" addrtype=\"ipv4\"/>\r\n" + //
    //             "        <hostnames>\r\n" + //
    //             "        </hostnames>\r\n" + //
    //             "        <ports>\r\n" + //
    //             "            <extraports state=\"closed\" count=\"49\">\r\n" + //
    //             "                <extrareasons reason=\"conn-refused\" count=\"49\" proto=\"tcp\" ports=\"21,23,25-26,53,80-81,110-111,113,135,139,143,179,199,443,445,465,514-515,548,554,587,646,993,995,1025-1027,1433,1720,1723,2000-2001,3306,3389,5060,5666,5900,6001,8000,8008,8080,8443,8888,10000,32768,49152,49154\"/>\r\n" + //
    //             "            </extraports>\r\n" + //
    //             "            <port protocol=\"tcp\" portid=\"22\">\r\n" + //
    //             "                <state state=\"open\" reason=\"syn-ack\" reason_ttl=\"0\"/>\r\n" + //
    //             "                <service name=\"ssh\" method=\"table\" conf=\"3\"/>\r\n" + //
    //             "            </port>\r\n" + //
    //             "        </ports>\r\n" + //
    //             "        <times srtt=\"178\" rttvar=\"146\" to=\"100000\"/>\r\n" + //
    //             "    </host>\r\n" + //
    //             "    <runstats>\r\n" + //
    //             "        <finished time=\"1705043335\" timestr=\"Thu Jan 11 23:08:55 2024\" summary=\"Nmap done at Thu Jan 11 23:08:55 2024; 256 IP addresses (2 hosts up) scanned in 9.97 seconds\" elapsed=\"9.97\" exit=\"success\"/>\r\n" + //
    //             "        <hosts up=\"2\" down=\"254\" total=\"256\"/>\r\n" + //
    //             "    </runstats>\r\n" + //
    //             "</nmaprun>"; // Complete XML here

    //     try {
    //         Scan scan = createScanFromXml(xml);
    //         System.out.println(scan.hashCode());
    //         // Do something with the scan object
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}

