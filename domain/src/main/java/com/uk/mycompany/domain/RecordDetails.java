package com.uk.mycompany.domain;

import java.util.List;

public class RecordDetails extends AbstractDomainObject {

    private String username;
    private String forename;
    private String surname;
    private String gender;
    private String emailAddress;
    private String reasonAttending;
    private List<String> interests;
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

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getReasonAttending() {
        return reasonAttending;
    }

    public void setReasonAttending(String reasonAttending) {
        this.reasonAttending = reasonAttending;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}