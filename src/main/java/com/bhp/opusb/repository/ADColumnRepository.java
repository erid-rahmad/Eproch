package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.view.AdColumnView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADColumn entity.
 */
@Repository
public interface ADColumnRepository extends JpaRepository<ADColumn, Long>, JpaSpecificationExecutor<ADColumn> {

  Optional<AdColumnView> findFirstByAdTable_NameAndImportedTable(String tableName, String importedTable);
  boolean existsByAdTable_NameAndSqlName(String tableName, String sqlName);
}
