package org.sfsu.cyber.nmaptracker.controller;

import java.util.Collection;

import org.sfsu.cyber.nmaptracker.data.Scan;
import org.sfsu.cyber.nmaptracker.data.ScanFactory;
import org.sfsu.cyber.nmaptracker.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/scans")
public class ApiController {

    @Autowired
    private ScanService scanService;

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Integer> receiveScan(@RequestBody String xmlData) {
        try {
            Scan scan = ScanFactory.createScanFromXml(xmlData);
            int hashCode = scanService.addScan(scan);
            return ResponseEntity.ok(hashCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping
    public Collection<Scan> getAllScans() {
        return scanService.getAllScans();
    }

    @GetMapping("/{hashCode}")
    public Scan getScanByHashCode(@PathVariable int hashCode) {
        return scanService.getScanByHashCode(hashCode)
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scan not found"));
    }

    @DeleteMapping("/{hashCode}")
    public ResponseEntity<?> removeScan(@PathVariable int hashCode) {
        boolean isRemoved = scanService.removeScan(hashCode);
        if (isRemoved) {
            return ResponseEntity.ok().build(); // Scan successfully removed
        } else {
            return ResponseEntity.notFound().build(); // Scan not found
        }
    }
}


