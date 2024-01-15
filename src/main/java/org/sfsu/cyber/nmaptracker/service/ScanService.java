package org.sfsu.cyber.nmaptracker.service;

import org.sfsu.cyber.nmaptracker.data.Scan;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Optional;

@Service
public class ScanService {
    private final ConcurrentHashMap<Integer, Scan> scans = new ConcurrentHashMap<>();

    public int addScan(Scan scan) {
        int key = scan.hashCode();
        scans.putIfAbsent(key, scan); // This ensures no duplicate scans are added
        return key;
    }

    public boolean removeScan(Scan scan) {
        int hashCode = scan.hashCode();
        return scans.remove(hashCode) != null;
    }
    
    public boolean removeScan(int hashCode) {
        return scans.remove(hashCode) != null;
    }

    public Collection<Scan> getAllScans() {
        return scans.values();
    }

    public Optional<Scan> getScanByHashCode(int hashCode) {
        return Optional.ofNullable(scans.get(hashCode));
    }

}
