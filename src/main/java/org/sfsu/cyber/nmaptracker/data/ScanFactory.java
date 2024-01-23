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
    // Utility method to convert a DOM Element back to a String representation
    private static String toString(Element element) throws IOException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(element), new StreamResult(writer));
        return writer.toString();
    }
}

