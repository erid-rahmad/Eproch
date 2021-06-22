package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MVendorEvaluation;
import com.bhp.opusb.service.dto.MVendorEvaluationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MVendorEvaluation} and its DTO {@link MVendorEvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractMapper.class, AdUserMapper.class})
public interface MVendorEvaluationMapper extends EntityMapper<MVendorEvaluationDTO, MVendorEvaluation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "contract.documentNo", target = "contractNo")
    @Mapping(source = "contract.name", target = "contractName")
    @Mapping(source = "contract.vendorEvaluation.id", target = "evaluationTypeId")
    @Mapping(source = "contract.vendorEvaluation.name", target = "evaluationTypeName")
    @Mapping(source = "contract.evaluationPeriod", target = "contractEvaluationPeriod")
    @Mapping(source = "reviewer.id", target = "reviewerUserId")
    @Mapping(source = "reviewer.user.login", target = "reviewerUserName")
    MVendorEvaluationDTO toDto(MVendorEvaluation mVendorEvaluation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "reviewerUserId", target = "reviewer")
    MVendorEvaluation toEntity(MVendorEvaluationDTO mVendorEvaluationDTO);

    default MVendorEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorEvaluation mVendorEvaluation = new MVendorEvaluation();
        mVendorEvaluation.setId(id);
        return mVendorEvaluation;
    }
}
