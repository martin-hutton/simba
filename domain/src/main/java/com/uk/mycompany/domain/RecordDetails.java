package com.uk.mycompany.domain;

public class RecordDetails extends AbstractDomainObject {

    private String username;
    private String timestamp;
    private String status;
    private String location;
    private Emotion emotion;

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(final Emotion emotion) {
        this.emotion = emotion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }
}