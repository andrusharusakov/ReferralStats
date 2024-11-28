package me.list_tw.referralstats.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity  // Убедитесь, что класс помечен как сущность
public class Referrals {

    @Id  // Убедитесь, что есть аннотация @Id для идентификатора
    private Long referralId;
    private Long invitedId;
    private String subscription;
    private Integer time;

    // Конструктор с параметрами
    public Referrals(Long referralId, Long invitedId, String subscription, Integer time) {
        this.referralId = referralId;
        this.invitedId = invitedId;
        this.subscription = subscription;
        this.time = time;
    }

    public Referrals() {

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
