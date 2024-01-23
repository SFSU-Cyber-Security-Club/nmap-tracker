package org.sfsu.cyber.nmaptracker.data;

import java.util.List;

public class Host {
    private long starttime;
    private long endtime;
    private Status status;
    private Address address;
    private List<Port> ports;
    private Times times;

    // Constructors
    public Host() {
    }

    public Host(long starttime, long endtime, Status status, Address address, List<Port> ports, Times times) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
        this.address = address;
        this.ports = ports;
        this.times = times;
    }

    // Getters and setters
    public String getAddr() {
        return address.getAddr();
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Times getTimes() {
        return times;
    }

    public void setTimes(Times times) {
        this.times = times;
    }

    public static class Status {
        private String state;
        private String reason;
        private int reasonTtl;

        // Constructors
        public Status() {
        }

        public Status(String state, String reason, int reasonTtl) {
            this.state = state;
            this.reason = reason;
            this.reasonTtl = reasonTtl;
        }

        // Getters and setters
        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getReasonTtl() {
            return reasonTtl;
        }

        public void setReasonTtl(int reasonTtl) {
            this.reasonTtl = reasonTtl;
        }
    }

    public static class Address {
        private String addr;
        private String addrtype;

        // Constructors
        public Address() {
        }

        public Address(String addr, String addrtype) {
            this.addr = addr;
            this.addrtype = addrtype;
        }

        // Getters and setters
        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAddrtype() {
            return addrtype;
        }

        public void setAddrtype(String addrtype) {
            this.addrtype = addrtype;
        }
    }

    public static class Times {
        private int srtt;
        private int rttvar;
        private int to;

        // Constructors
        public Times() {
        }

        public Times(int srtt, int rttvar, int to) {
            this.srtt = srtt;
            this.rttvar = rttvar;
            this.to = to;
        }

        // Getters and setters
        public int getSrtt() {
            return srtt;
        }

        public void setSrtt(int srtt) {
            this.srtt = srtt;
        }

        public int getRttvar() {
            return rttvar;
        }

        public void setRttvar(int rttvar) {
            this.rttvar = rttvar;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }
    }
}
