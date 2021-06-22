package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.service.dto.MRfqDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MRfq} and its DTO {@link MRfqDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBusinessCategoryMapper.class, CCurrencyMapper.class, CWarehouseMapper.class, CCostCenterMapper.class, CDocumentTypeMapper.class})
public interface MRfqMapper extends EntityMapper<MRfqDTO, MRfq> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "businessClassification.id", target = "businessClassificationId")
    @Mapping(source = "businessClassification.name", target = "businessClassificationName")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
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
