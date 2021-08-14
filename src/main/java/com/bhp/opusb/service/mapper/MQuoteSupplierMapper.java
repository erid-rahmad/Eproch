package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MQuoteSupplierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MQuoteSupplier} and its DTO {@link MQuoteSupplierDTO}.
 */
@Mapper(componentModel = "spring", uses = {MRfqMapper.class, CBusinessCategoryMapper.class, CVendorMapper.class})
public interface MQuoteSupplierMapper extends EntityMapper<MQuoteSupplierDTO, MQuoteSupplier> {

    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "businessClassification.id", target = "businessClassificationId")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessSubCategory.id", target = "businessSubCategoryId")
    @Mapping(source = "vendor.id", target = "vendorId")
    MQuoteSupplierDTO toDto(MQuoteSupplier mQuoteSupplier);

    @Mapping(source = "quotationId", target = "quotation")
    @Mapping(source = "businessClassificationId", target = "businessClassification")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "businessSubCategoryId", target = "businessSubCategory")
    @Mapping(source = "vendorId", target = "vendor")
    MQuoteSupplier toEntity(MQuoteSupplierDTO mQuoteSupplierDTO);

    default MQuoteSupplier fromId(Long id) {
        if (id == null) {
            return null;
        }
        MQuoteSupplier mQuoteSupplier = new MQuoteSupplier();
        mQuoteSupplier.setId(id);
        return mQuoteSupplier;
    }
}
