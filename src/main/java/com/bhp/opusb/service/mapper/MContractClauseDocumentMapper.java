package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractClauseDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractClauseDocument} and its DTO {@link MContractClauseDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractMapper.class})
public interface MContractClauseDocumentMapper extends EntityMapper<MContractClauseDocumentDTO, MContractClauseDocument> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contract.id", target = "contractId")
    MContractClauseDocumentDTO toDto(MContractClauseDocument mContractClauseDocument);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractId", target = "contract")
    MContractClauseDocument toEntity(MContractClauseDocumentDTO mContractClauseDocumentDTO);

    default MContractClauseDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractClauseDocument mContractClauseDocument = new MContractClauseDocument();
        mContractClauseDocument.setId(id);
        return mContractClauseDocument;
    }
}
