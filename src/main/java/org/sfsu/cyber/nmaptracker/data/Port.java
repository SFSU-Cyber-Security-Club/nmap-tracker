package org.sfsu.cyber.nmaptracker.data;

public class Port {
    private String protocol;
    private int portid;
    private State state;
    private Service service;

    // Constructors
    public Port() {}

    public Port(String protocol, int portid, State state, Service service) {
        this.protocol = protocol;
        this.portid = portid;
        this.state = state;
        this.service = service;
    }

    // Getters and setters
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPortid() {
        return portid;
    }

    public void setPortid(int portid) {
        this.portid = portid;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public static class State {
        private String state;
        private String reason;
        private int reasonTtl;

        // Constructors
        public State() {}

        public State(String state, String reason, int reasonTtl) {
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

    public static class Service {
        private String name;
        private String method;
        private int conf;

        // Constructors
        public Service() {}

        public Service(String name, String method, int conf) {
            this.name = name;
            this.method = method;
            this.conf = conf;
        }

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public int getConf() {
            return conf;
        }

        public void setConf(int conf) {
            this.conf = conf;
        }
    }
}

