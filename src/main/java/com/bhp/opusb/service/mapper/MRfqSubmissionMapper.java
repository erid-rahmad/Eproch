package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MRfqSubmissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MRfqSubmission} and its DTO {@link MRfqSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MRfqMapper.class, MQuoteSupplierMapper.class, ADOrganizationMapper.class, CBusinessCategoryMapper.class, CCurrencyMapper.class, CWarehouseMapper.class, CCostCenterMapper.class})
public interface MRfqSubmissionMapper extends EntityMapper<MRfqSubmissionDTO, MRfqSubmission> {

    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "quoteSupplier.id", target = "quoteSupplierId")
    @Mapping(source = "quoteSupplier.vendor.name", target = "vendorName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "businessClassification.id", target = "businessClassificationId")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    MRfqSubmissionDTO toDto(MRfqSubmission mRfqSubmission);

    @Mapping(source = "quotationId", target = "quotation")
    @Mapping(source = "quoteSupplierId", target = "quoteSupplier")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "businessClassificationId", target = "businessClassification")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "costCenterId", target = "costCenter")
    MRfqSubmission toEntity(MRfqSubmissionDTO mRfqSubmissionDTO);

    default MRfqSubmission fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRfqSubmission mRfqSubmission = new MRfqSubmission();
        mRfqSubmission.setId(id);
        return mRfqSubmission;
    }
}
