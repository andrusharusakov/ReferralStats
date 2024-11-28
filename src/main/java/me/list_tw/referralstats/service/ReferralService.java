package me.list_tw.referralstats.service;

import me.list_tw.referralstats.model.Referrals;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ReferralService {

    private static final Map<String, Integer> subscriptionPrices = new HashMap<>();
    static {
        subscriptionPrices.put("VPN Lite 30", 145);
        subscriptionPrices.put("VPN Lite 180", 695);
        subscriptionPrices.put("VPN Lite 365", 1195);
        subscriptionPrices.put("VPN Pro 30", 245);
        subscriptionPrices.put("VPN Pro 180", 895);
        subscriptionPrices.put("VPN Pro 365", 1745);
    }

    // Мок-данные вместо работы с базой данных
    public Map<String, Object> getReferralStats(Long referralId) {
        // Здесь замените данные на жестко закодированные или из другого источника
        List<Referrals> referrals = getFakeReferralData(referralId);

        int transitioned = 0;
        int purchased = 0;
        Map<String, Integer> subscriptionCounts = new HashMap<>();
        int totalAmount = 0;

        for (Referrals referral : referrals) {
            if (referral.getSubscription() == null) {
                transitioned++;
            } else {
                purchased++;
                String subscriptionKey = referral.getSubscription() + " " + referral.getTime();
                subscriptionCounts.put(subscriptionKey, subscriptionCounts.getOrDefault(subscriptionKey, 0) + 1);
                totalAmount += subscriptionPrices.get(subscriptionKey);
            }
        }

        double partnerShare = totalAmount * 0.5;

        Map<String, Object> stats = new HashMap<>();
        stats.put("transitioned", transitioned);
        stats.put("purchased", purchased);
        stats.put("subscriptionCounts", subscriptionCounts);
        stats.put("totalAmount", totalAmount);
        stats.put("partnerShare", partnerShare);

        return stats;
    }

    // Метод для получения моковых данных
    private List<Referrals> getFakeReferralData(Long referralId) {
        // Возвращаем моковые данные для примера
        return List.of(
                new Referrals(referralId, 1L, "VPN Pro 30", 30),
                new Referrals(referralId, 2L, "VPN Lite 180", 180)
        );
    }
}
