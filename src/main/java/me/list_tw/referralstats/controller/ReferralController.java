package me.list_tw.referralstats.controller;

import me.list_tw.referralstats.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @GetMapping("/referralStats")
    public String getReferralStats(@RequestParam Long referralId, Model model) {
        // Получаем статистику для партнера с заданным referralId
        var stats = referralService.getReferralStats(referralId);

        // Добавляем данные в модель для отображения
        model.addAttribute("transitioned", stats.get("transitioned"));
        model.addAttribute("purchased", stats.get("purchased"));
        model.addAttribute("subscriptionCounts", stats.get("subscriptionCounts"));
        model.addAttribute("totalAmount", stats.get("totalAmount"));
        model.addAttribute("partnerShare", stats.get("partnerShare"));

        // Возвращаем название HTML-шаблона
        return "referralStats";  // Этот файл будет отображен
    }
}
