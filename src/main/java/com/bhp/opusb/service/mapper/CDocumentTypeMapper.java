package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.service.dto.CDocumentTypeDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CDocumentType} and its DTO {@link CDocumentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CDocumentTypeMapper extends EntityMapper<CDocumentTypeDTO, CDocumentType> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CDocumentTypeDTO toDto(CDocumentType cDocumentType);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CDocumentType toEntity(CDocumentTypeDTO cDocumentTypeDTO);

    default CDocumentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CDocumentType cDocumentType = new CDocumentType();
        cDocumentType.setId(id);
        return cDocumentType;
    }
}
