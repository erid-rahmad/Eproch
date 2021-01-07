package com.bhp.opusb.service;

import com.bhp.opusb.service.dto.marketplace.MProductCatalogAdaptableDTO;

import reactor.core.publisher.Mono;

public interface MarketplaceProductService<T extends MProductCatalogAdaptableDTO> {
  Mono<T> findByCriteria();
}
