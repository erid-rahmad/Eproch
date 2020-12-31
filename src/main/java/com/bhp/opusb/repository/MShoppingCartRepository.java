package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MShoppingCart;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MShoppingCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MShoppingCartRepository extends JpaRepository<MShoppingCart, Long>, JpaSpecificationExecutor<MShoppingCart> {
}
