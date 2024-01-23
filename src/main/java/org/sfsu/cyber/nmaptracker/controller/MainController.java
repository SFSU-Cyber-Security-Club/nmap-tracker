package org.sfsu.cyber.nmaptracker.controller;

import java.util.Collection;

import org.sfsu.cyber.nmaptracker.data.Scan;
import org.sfsu.cyber.nmaptracker.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final ScanService scanService;

    public MainController(ScanService scanService) {
        this.scanService = scanService;
    }

    @GetMapping("/")
    public String showScans(Model model) {
        Collection<Scan> allScans = scanService.getAllScans();
        model.addAttribute("scans", allScans);
        return "main"; // The name of the Thymeleaf template
    }
}
