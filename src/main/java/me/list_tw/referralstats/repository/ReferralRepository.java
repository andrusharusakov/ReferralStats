package me.list_tw.referralstats.repository;

import me.list_tw.referralstats.model.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {

    List<Referral> findByReferralId(Long referralId);
}
