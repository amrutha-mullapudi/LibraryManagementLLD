package library.model;

import library.model.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class Notification {
    private String id;
    private String recipientId;
    private NotificationType type;
    private String subject;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> metadata;
    private boolean sent;

    public Notification() {
        this.timestamp = LocalDateTime.now();
        this.metadata = new HashMap<>();
        this.sent = false;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void addMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}