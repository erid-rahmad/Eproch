package com.bhp.opusb.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.ScAuthority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScAuthority entity.
 */
@Repository
public interface ScAuthorityRepository extends JpaRepository<ScAuthority, Long>, JpaSpecificationExecutor<ScAuthority> {

  Optional<ScAuthority> findByAuthorityName(String authorityName);
  List<ScAuthority> findByAuthorityNameIn(Collection<String> authorityNames);
}
