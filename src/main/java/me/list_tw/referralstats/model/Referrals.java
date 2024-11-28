package me.list_tw.referralstats.model;

public class Referrals {

    private Long referralId;
    private Long invitedId;
    private String subscription;
    private Integer time;

    // Конструктор
    public Referrals(Long referralId, Long invitedId, String subscription, Integer time) {
        this.referralId = referralId;
        this.invitedId = invitedId;
        this.subscription = subscription;
        this.time = time;
    }

    // Геттеры и сеттеры
    public Long getReferralId() {
        return referralId;
    }

    public void setReferralId(Long referralId) {
        this.referralId = referralId;
    }

    public Long getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(Long invitedId) {
        this.invitedId = invitedId;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
