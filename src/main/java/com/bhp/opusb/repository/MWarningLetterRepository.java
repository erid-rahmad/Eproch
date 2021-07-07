package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MWarningLetter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MWarningLetter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MWarningLetterRepository extends JpaRepository<MWarningLetter, Long>, JpaSpecificationExecutor<MWarningLetter> {
}
