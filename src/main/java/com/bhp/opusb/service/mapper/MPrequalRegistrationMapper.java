package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalRegistrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalRegistration} and its DTO {@link MPrequalRegistrationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalAnnouncementMapper.class, MPrequalificationInformationMapper.class, CVendorMapper.class})
public interface MPrequalRegistrationMapper extends EntityMapper<MPrequalRegistrationDTO, MPrequalRegistration> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "announcement.id", target = "announcementId")
    @Mapping(source = "announcement.description", target = "announcementDescription")
    @Mapping(source = "announcement.publishDate", target = "announcementPublishDate")
    @Mapping(source = "announcement.prequalificationSchedule.endDate", target = "announcementEndDate")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.code", target = "vendorCode")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "vendor.type", target = "vendorType")
    MPrequalRegistrationDTO toDto(MPrequalRegistration mPrequalRegistration);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "announcementId", target = "announcement")
    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "vendorId", target = "vendor")
    MPrequalRegistration toEntity(MPrequalRegistrationDTO mPrequalRegistrationDTO);

    default MPrequalRegistration fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalRegistration mPrequalRegistration = new MPrequalRegistration();
        mPrequalRegistration.setId(id);
        return mPrequalRegistration;
    }
}
