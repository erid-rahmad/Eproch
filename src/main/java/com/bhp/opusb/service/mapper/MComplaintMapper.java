package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MComplaintDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MComplaint} and its DTO {@link MComplaintDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CVendorMapper.class, CCostCenterMapper.class, MContractMapper.class, CBusinessCategoryMapper.class})
public interface MComplaintMapper extends EntityMapper<MComplaintDTO, MComplaint> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "contract.documentNo", target = "contractNo")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "subBusinessCategory.id", target = "subBusinessCategoryId")
    @Mapping(source = "subBusinessCategory.name", target = "subBusinessCategoryName")
    MComplaintDTO toDto(MComplaint mComplaint);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "subBusinessCategoryId", target = "subBusinessCategory")
    MComplaint toEntity(MComplaintDTO mComplaintDTO);

    default MComplaint fromId(Long id) {
        if (id == null) {
            return null;
        }
        MComplaint mComplaint = new MComplaint();
        mComplaint.setId(id);
        return mComplaint;
    }
}
