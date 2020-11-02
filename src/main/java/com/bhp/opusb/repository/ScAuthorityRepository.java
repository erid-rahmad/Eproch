package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.ScAuthority;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScAuthority entity.
 */
@Repository
public interface ScAuthorityRepository extends JpaRepository<ScAuthority, Long>, JpaSpecificationExecutor<ScAuthority> {

  Optional<ScAuthority> findByAuthorityName(String authorityName);
}
