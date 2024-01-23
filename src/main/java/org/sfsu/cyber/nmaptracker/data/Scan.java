package org.sfsu.cyber.nmaptracker.data;

import java.util.List;
import java.util.Objects;

public class Scan {
    private String scanner;
    private String args;
    private long start;
    private String startstr;
    private String version;
    private String xmloutputversion;
    private List<Host> hosts;
    private RunStats runstats;

    // Constructors
    public Scan() {
    }

    public Scan(String scanner, String args, long start, String startstr, String version, String xmloutputversion,
            List<Host> hosts, RunStats runstats) {
        this.scanner = scanner;
        this.args = args;
        this.start = start;
        this.startstr = startstr;
        this.version = version;
        this.xmloutputversion = xmloutputversion;
        this.hosts = hosts;
        this.runstats = runstats;
    }

    // needed to check for dupes in a HashSet of Scans
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Scan scan = (Scan) o;
        return start == scan.start &&
                Objects.equals(args, scan.args);
    }

    @Override
    public int hashCode() {
        // potentially an issue if args and start isn't enough to avoid collisions
        return Objects.hash(args, start);
    }

    public boolean containsAddr(String address, boolean patternMatch) {
        for (Host host : this.hosts) {
            if (patternMatch) {
                if (host.getAddr().toLowerCase().matches(address.toLowerCase())) {
                    return true;
                }
            } else {
                if (host.getAddr().equals(address)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsAddr(String address) {
        return containsAddr(address, true);
    }

    public boolean containsPort(String port, boolean patternMatch) {
        for (Host host : this.hosts) {
            for (Port p : host.getPorts()) {
                if (patternMatch) {
                    if (p.getPortidString().toLowerCase().matches(port.toLowerCase())) {
                        return true;
                    }
                } else {
                    if (p.getPortidString().equals(port)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean containsPort(String port) {
        return containsPort(port, true);
    }

    // Getters and setters
    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner = scanner;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public String getStartstr() {
        return startstr;
    }

    public void setStartstr(String startstr) {
        this.startstr = startstr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getXmloutputversion() {
        return xmloutputversion;
    }

    public void setXmloutputversion(String xmloutputversion) {
        this.xmloutputversion = xmloutputversion;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public RunStats getRunstats() {
        return runstats;
    }

    public void setRunstats(RunStats runstats) {
        this.runstats = runstats;
    }

    public static class RunStats {
        private long time;
        private String timestr;
        private String summary;
        private double elapsed;
        private String exit;
        private int up;
        private int down;
        private int total;

        // Constructors
        public RunStats() {
        }

        public RunStats(long time, String timestr, String summary, double elapsed, String exit, int up, int down,
                int total) {
            this.time = time;
            this.timestr = timestr;
            this.summary = summary;
            this.elapsed = elapsed;
            this.exit = exit;
            this.up = up;
            this.down = down;
            this.total = total;
        }

        // Getters and setters
        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTimestr() {
            return timestr;
        }

        public void setTimestr(String timestr) {
            this.timestr = timestr;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public double getElapsed() {
            return elapsed;
        }

        public void setElapsed(double elapsed) {
            this.elapsed = elapsed;
        }

        public String getExit() {
            return exit;
        }

        public void setExit(String exit) {
            this.exit = exit;
        }

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {
            this.down = down;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
