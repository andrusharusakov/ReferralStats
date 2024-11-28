package me.list_tw.referralstats.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Referrals {

    @Id
    private Long referralId;
    private Long invitedId;
    private String subscription;
    private Integer time;

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
