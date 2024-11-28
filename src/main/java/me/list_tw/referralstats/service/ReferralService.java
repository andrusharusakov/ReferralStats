package me.list_tw.referralstats.service;

import me.list_tw.referralstats.model.Referral;
import me.list_tw.referralstats.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ReferralService {

    @Autowired
    private ReferralRepository referralRepository;

    private static final Map<String, Integer> subscriptionPrices = new HashMap<>();
    static {
        subscriptionPrices.put("VPN Lite 30", 145);
        subscriptionPrices.put("VPN Lite 180", 695);
        subscriptionPrices.put("VPN Lite 365", 1195);
        subscriptionPrices.put("VPN Pro 30", 245);
        subscriptionPrices.put("VPN Pro 180", 895);
        subscriptionPrices.put("VPN Pro 365", 1745);
    }

    public Map<String, Object> getReferralStats(Long referralId) {
        List<Referral> referrals = referralRepository.findByReferralId(referralId);

        // Подсчёт пользователей, которые перешли и купили
        int transitioned = 0;
        int purchased = 0;
        Map<String, Integer> subscriptionCounts = new HashMap<>();
        int totalAmount = 0;

        for (Referral referral : referrals) {
            if (referral.getSubscription() == null) {
                transitioned++;
            } else {
                purchased++;
                String subscriptionKey = referral.getSubscription() + " " + referral.getTime();
                subscriptionCounts.put(subscriptionKey, subscriptionCounts.getOrDefault(subscriptionKey, 0) + 1);
                totalAmount += subscriptionPrices.get(subscriptionKey);
            }
        }

        // Рассчитываем долю партнёра
        double partnerShare = totalAmount * 0.5;

        // Формируем данные для отображения
        Map<String, Object> stats = new HashMap<>();
        stats.put("transitioned", transitioned);
        stats.put("purchased", purchased);
        stats.put("subscriptionCounts", subscriptionCounts);
        stats.put("totalAmount", totalAmount);
        stats.put("partnerShare", partnerShare);

        return stats;
    }
}
