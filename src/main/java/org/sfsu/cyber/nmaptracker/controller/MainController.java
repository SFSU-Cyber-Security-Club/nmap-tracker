package org.sfsu.cyber.nmaptracker.controller;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.sfsu.cyber.nmaptracker.data.Scan;
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

        if (filterByAddr != null && !filterByAddr.isEmpty()) {
            allScans = allScans.stream().filter(scan -> scan.containsAddr(filterByAddr)).collect(Collectors.toList());

            System.out.println("filterByAddr request: " + filterByAddr);
            model.addAttribute("filterByAddr", filterByAddr);
        }

        if (filterByPort != null && !filterByPort.isEmpty()) {
            allScans = allScans.stream().filter(scan -> scan.containsPort(filterByPort)).collect(Collectors.toList());
            System.out.println("filterByPort request: " + filterByPort);
            model.addAttribute("filterByPort", filterByPort);
        }

        model.addAttribute("scans", allScans);
        return "main"; // The name of the Thymeleaf template
    }
}
