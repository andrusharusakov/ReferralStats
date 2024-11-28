package me.list_tw.referralstats.service;

import me.list_tw.referralstats.model.Referrals;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ReferralService {

    private final DataSource dataSource;

    // Получаем строку подключения к базе данных из конфигурации
    public ReferralService(DataSource dataSource) {
        this.dataSource = dataSource;
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
        List<Referrals> referrals = new ArrayList<>();

        String query = "SELECT referralId, invitedId, subscription, time FROM referrals WHERE referralId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, referralId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Long referralIdFromDb = rs.getLong("referralId");
                    Long invitedId = rs.getLong("invitedId");
                    String subscription = rs.getString("subscription");
                    int time = rs.getInt("time");

                    referrals.add(new Referrals(referralIdFromDb, invitedId, subscription, time));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
        }

        return referrals;
    }
}
