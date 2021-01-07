package com.bhp.opusb.service.dto.marketplace;

import com.bhp.opusb.service.dto.MProductCatalogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MProductCatalogAdaptableDTO {
  Page<MProductCatalogDTO> map(Pageable page);
}
