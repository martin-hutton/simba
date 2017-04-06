package com.uk.mycompany.domain;

public class Emotion extends AbstractDomainObject {

    private Double anger;
    private Double contempt;
    private Double disgust;
    private Double fear;
    private Double happiness;
    private Double neutral;
    private Double sadness;
    private Double surprise;

    public Double getAnger() {
        return anger;
    }

    public void setAnger(final Double anger) {
        this.anger = anger;
    }

    public Double getContempt() {
        return contempt;
    }

    public void setContempt(final Double contempt) {
        this.contempt = contempt;
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(final Double disgust) {
        this.disgust = disgust;
    }

    public Double getFear() {
        return fear;
    }

    public void setFear(final Double fear) {
        this.fear = fear;
    }

    public Double getHappiness() {
        return happiness;
    }

    public void setHappiness(final Double happiness) {
        this.happiness = happiness;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(final Double neutral) {
        this.neutral = neutral;
    }

    public Double getSadness() {
        return sadness;
    }

    public void setSadness(final Double sadness) {
        this.sadness = sadness;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(final Double surprise) {
        this.surprise = surprise;
    }
}