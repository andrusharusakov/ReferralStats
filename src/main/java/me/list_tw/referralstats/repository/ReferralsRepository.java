package me.list_tw.referralstats.repository;

import me.list_tw.referralstats.model.Referrals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralsRepository extends JpaRepository<Referrals, Long> {
    List<Referrals> findByReferralId(Long referralId);
}
