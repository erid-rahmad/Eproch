package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.service.dto.ADTabDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ADTab} and its DTO {@link ADTabDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADTableMapper.class, ADColumnMapper.class, ADWindowMapper.class})
public interface ADTabMapper extends EntityMapper<ADTabDTO, ADTab> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTable.id", target = "adTableId")
    @Mapping(source = "adTable.name", target = "adTableName")
    @Mapping(source = "parentColumn.id", target = "parentColumnId")
    @Mapping(source = "parentColumn.name", target = "parentColumnName")
    @Mapping(source = "parentColumn.sqlName", target = "parentColumnSqlName")
    @Mapping(source = "foreignColumn.id", target = "foreignColumnId")
    @Mapping(source = "foreignColumn.name", target = "foreignColumnName")
    @Mapping(source = "foreignColumn.sqlName", target = "foreignColumnSqlName")
    @Mapping(source = "adWindow.id", target = "adWindowId")
    @Mapping(source = "adWindow.name", target = "adWindowName")
    @Mapping(source = "parentTab.id", target = "parentTabId")
    @Mapping(source = "parentTab.name", target = "parentTabName")
    ADTabDTO toDto(ADTab aDTab);

    @Mapping(target = "aDTabs", ignore = true)
    @Mapping(target = "removeADTab", ignore = true)
    @Mapping(target = "aDFields", ignore = true)
    @Mapping(target = "removeADField", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTableId", target = "adTable")
    @Mapping(source = "parentColumnId", target = "parentColumn")
    @Mapping(source = "foreignColumnId", target = "foreignColumn")
    @Mapping(source = "adWindowId", target = "adWindow")
    @Mapping(source = "parentTabId", target = "parentTab")
    ADTab toEntity(ADTabDTO aDTabDTO);

    default ADTab fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADTab aDTab = new ADTab();
        aDTab.setId(id);
        return aDTab;
    }
}
