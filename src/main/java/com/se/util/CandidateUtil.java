package com.se.util;

public class CandidateUtil {
    private String _class;
    private String score;
    private String isVerified;
    private String effect;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public CandidateUtil(String _class, String score, String isVerified, String effect) {
        this._class = _class;
        this.score = score;
        this.isVerified = isVerified;
        this.effect = effect;
    }
}
