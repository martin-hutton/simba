package com.uk.mycompany.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mahutton on 12/04/2017.
 */
public class Devise extends AbstractDomainObject {

    private ProfileDetails profileDetails;

    private Map<String, String> weekCheckInStatus = new HashMap<>();

    private Emotion emotion;

    private Set<String> skills;

    public ProfileDetails getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(ProfileDetails profileDetails) {
        this.profileDetails = profileDetails;
    }

    public Map<String, String> getWeekCheckInStatus() {
        return weekCheckInStatus;
    }

    public void setWeekCheckInStatus(Map<String, String> weekCheckInStatus) {
        this.weekCheckInStatus = weekCheckInStatus;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }
}
