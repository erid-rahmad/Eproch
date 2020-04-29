package com.bhp.opusb.service.mapper;

import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.service.dto.ADClientDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ADClient} and its DTO {@link ADClientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ADClientMapper extends EntityMapper<ADClientDTO, ADClient> {

    @Mapping(target = "aDOrganizations", ignore = true)
    @Mapping(target = "removeADOrganization", ignore = true)
    ADClient toEntity(ADClientDTO aDClientDTO);

    default ADClient fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADClient aDClient = new ADClient();
        aDClient.setId(id);
        return aDClient;
    }
}
