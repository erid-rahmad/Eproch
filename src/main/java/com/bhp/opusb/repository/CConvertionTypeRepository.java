package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CConvertionType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CConvertionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CConvertionTypeRepository extends JpaRepository<CConvertionType, Long>, JpaSpecificationExecutor<CConvertionType> {
}
