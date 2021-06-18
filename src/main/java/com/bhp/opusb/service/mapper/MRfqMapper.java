package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MRfqDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MRfq} and its DTO {@link MRfqDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBusinessCategoryMapper.class, CCurrencyMapper.class, CWarehouseMapper.class, CCostCenterMapper.class, CDocumentTypeMapper.class})
public interface MRfqMapper extends EntityMapper<MRfqDTO, MRfq> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "businessClassification.id", target = "businessClassificationId")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    MRfqDTO toDto(MRfq mRfq);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "businessClassificationId", target = "businessClassification")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "documentTypeId", target = "documentType")
    MRfq toEntity(MRfqDTO mRfqDTO);

    default MRfq fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRfq mRfq = new MRfq();
        mRfq.setId(id);
        return mRfq;
    }
}
