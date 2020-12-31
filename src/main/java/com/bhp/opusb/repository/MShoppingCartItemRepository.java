package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MShoppingCartItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MShoppingCartItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MShoppingCartItemRepository extends JpaRepository<MShoppingCartItem, Long>, JpaSpecificationExecutor<MShoppingCartItem> {
}
