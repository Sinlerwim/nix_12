package model;

import java.time.LocalDateTime;

public class Visitor {
    String ipAddress;
    String userAgent;
    LocalDateTime time;

    public Visitor(String ipAddress, String userAgent, LocalDateTime time) {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.time = time;
    }

    @Override
    public String toString() {
        return time +" " + ipAddress + " " + userAgent;
    }
}
