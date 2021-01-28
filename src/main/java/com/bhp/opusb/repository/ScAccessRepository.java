package com.bhp.opusb.repository;

import java.util.Collection;
import java.util.List;

import com.bhp.opusb.domain.ScAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScAccess entity.
 */
@Repository
public interface ScAccessRepository extends JpaRepository<ScAccess, Long>, JpaSpecificationExecutor<ScAccess> {

  List<ScAccess> findByAuthority_Authority_NameInAndType_NameIn(
    Collection<String> authorityNames, Collection<String> typeNames);
}
