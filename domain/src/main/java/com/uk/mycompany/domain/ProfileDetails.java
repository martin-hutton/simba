package com.uk.mycompany.domain;

import com.uk.mycompany.domain.enumeration.Gender;

import java.util.Date;
import java.util.List;

/**
 * Created by mahutton on 12/04/2017.
 */
public class ProfileDetails extends AbstractDomainObject {

    private String username;

    private String forename;

    private String surname;

    private Gender gender;

    private String emailAddress;

    private String reasonAttending;

    private Date timestamp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
