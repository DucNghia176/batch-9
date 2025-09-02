package Assignment.entity;

import java.time.LocalDateTime;

public class LogEntry {
    private LocalDateTime timestamp;
    private String level;
    private String service;
    private String message;

    public LogEntry(String service, String message, String level, LocalDateTime timestamp) {
        this.service = service;
        this.message = message;
        this.level = level;
        this.timestamp = timestamp;
    }

    public String getLevel() {
        return level;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public String getService() {
        return service;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "service='" + service + '\'' +
                ", timestamp=" + timestamp +
                ", level='" + level + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
