package org.sfsu.cyber.nmaptracker.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.sfsu.cyber.nmaptracker.data.Scan;
import org.sfsu.cyber.nmaptracker.data.Host;
import org.sfsu.cyber.nmaptracker.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final ScanService scanService;

    public MainController(ScanService scanService) {
        this.scanService = scanService;
    }

    @GetMapping("/")
    public String showScans(@RequestParam(value = "filterByAddr", required = false) String filterByAddr,
            @RequestParam(value = "filterByPort", required = false) String filterByPort, Model model) {
        Collection<Scan> allScans = scanService.getAllScans();
        // make a copy of allScans to avoid modifying the original provided by scanService
        allScans = allScans.stream().map(scan -> new Scan(scan)).collect(Collectors.toList());

        if (filterByAddr != null && !filterByAddr.isEmpty()) {
            // Leaving this commented code below, incase we want to implement later
            // this code will show the entire scan (all hosts) if any host in the scan
            // matches the filterByAddr
            // allScans = allScans.stream().filter(scan ->
            // scan.containsAddr(filterByAddr)).collect(Collectors.toList());

            // Current implementation of filterByAddr is to show only hosts that match the
            // filterByAddr
            // even if it means hiding host that are part of the same scan, but do not match
            // the filterByAddr
            for (int i = 0; i < allScans.size(); i++) {
                Scan scan = (Scan) allScans.toArray()[i];
                List<Host> hosts = scan.getHosts();

                // filter hosts to only include hosts that contains address filterByAddr
                hosts = hosts.stream().filter(host -> host.containsAddr(filterByAddr)).collect(Collectors.toList());
                scan.setHosts(hosts);
            }

            System.out.println("filterByAddr request: " + filterByAddr);
            model.addAttribute("filterByAddr", filterByAddr);
        }

        // filterByPort is a secondary filter to filterByAddr
        if (filterByPort != null && !filterByPort.isEmpty()) {
            // Leaving this commented code below, incase we want to implement later
            // allScans = allScans.stream().filter(scan ->
            // scan.containsPort(filterByPort)).collect(Collectors.toList());
            for (int i = 0; i < allScans.size(); i++) {
                Scan scan = (Scan) allScans.toArray()[i];
                List<Host> hosts = scan.getHosts();

                // will filter hosts if one port matches
                hosts = hosts.stream().filter(host -> host.containsPort(filterByPort)).collect(Collectors.toList());
                scan.setHosts(hosts);
            }
            System.out.println("filterByPort request: " + filterByPort);
            model.addAttribute("filterByPort", filterByPort);
        }

        model.addAttribute("scans", allScans);
        return "main"; // The name of the Thymeleaf template
    }
}
