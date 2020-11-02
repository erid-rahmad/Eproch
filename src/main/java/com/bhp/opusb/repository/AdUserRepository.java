package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.AdUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdUser entity.
 */
@Repository
public interface AdUserRepository extends JpaRepository<AdUser, Long>, JpaSpecificationExecutor<AdUser> {

  Optional<AdUser> findByUserLogin(String login);
}
