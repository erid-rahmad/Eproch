package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.service.dto.MContractDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MContract} and its DTO {@link MContractDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingMapper.class, CCostCenterMapper.class, CDocumentTypeMapper.class, AdUserMapper.class, CVendorMapper.class, CVendorEvaluationMapper.class})
public interface MContractMapper extends EntityMapper<MContractDTO, MContract> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "pic.id", target = "picUserId")
    @Mapping(source = "pic.user.login", target = "picUserName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "vendorEvaluation.id", target = "vendorEvaluationId")
    @Mapping(source = "vendorEvaluation.name", target = "vendorEvaluationName")
    MContractDTO toDto(MContract mContract);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "picUserId", target = "pic")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "vendorEvaluationId", target = "vendorEvaluation")
    MContract toEntity(MContractDTO mContractDTO);

    default MContract fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContract mContract = new MContract();
        mContract.setId(id);
        return mContract;
    }
}
