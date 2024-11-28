package me.list_tw.referralstats.service;

import me.list_tw.referralstats.model.Referrals;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ReferralService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReferralService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Map<String, Integer> subscriptionPrices = new HashMap<>();
    static {
        subscriptionPrices.put("VPN Lite 30", 145);
        subscriptionPrices.put("VPN Lite 180", 695);
        subscriptionPrices.put("VPN Lite 365", 1195);
        subscriptionPrices.put("VPN Pro 30", 245);
        subscriptionPrices.put("VPN Pro 180", 895);
        subscriptionPrices.put("VPN Pro 365", 1745);
    }

    // Метод для получения статистики
    public Map<String, Object> getReferralStats(Long referralId) {
        List<Referrals> referrals = getReferralsFromDatabase(referralId);

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

    // Метод для получения данных из базы данных
    private List<Referrals> getReferralsFromDatabase(Long referralId) {
        String sql = "SELECT referralId, invitedId, subscription, time FROM referrals WHERE referralId = ?";

        return jdbcTemplate.query(sql, new Object[] { referralId }, (rs, rowNum) -> {
            Long referralIdFromDb = rs.getLong("referralId");
            Long invitedId = rs.getLong("invitedId");
            String subscription = rs.getString("subscription");
            int time = rs.getInt("time");

            return new Referrals(referralIdFromDb, invitedId, subscription, time);
        });
    }
}
