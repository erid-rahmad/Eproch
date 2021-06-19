package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MContractDocument;
import com.bhp.opusb.service.dto.MContractDocumentDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MContractDocument} and its DTO {@link MContractDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractMapper.class, CAttachmentMapper.class})
public interface MContractDocumentMapper extends EntityMapper<MContractDocumentDTO, MContractDocument> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "contract.name", target = "contractName")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "attachmentName")
    @Mapping(source = "attachment.downloadUrl", target = "attachmentUrl")
    MContractDocumentDTO toDto(MContractDocument mContractDocument);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "attachmentId", target = "attachment")
    MContractDocument toEntity(MContractDocumentDTO mContractDocumentDTO);

    default MContractDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractDocument mContractDocument = new MContractDocument();
        mContractDocument.setId(id);
        return mContractDocument;
    }
}
