package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CConvertionRateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CConvertionRate} and its DTO {@link CConvertionRateDTO}.
 */
@Mapper(componentModel = "spring", uses = {CCurrencyMapper.class, CConvertionTypeMapper.class, ADOrganizationMapper.class})
public interface CConvertionRateMapper extends EntityMapper<CConvertionRateDTO, CConvertionRate> {

    @Mapping(source = "sourceCurrency.id", target = "sourceCurrencyId")
    @Mapping(source = "sourceCurrency.name", target = "sourceCurrencyName")
    @Mapping(source = "targetCurrency.id", target = "targetCurrencyId")
    @Mapping(source = "targetCurrency.name", target = "targetCurrencyName")
    @Mapping(source = "convertionType.id", target = "convertionTypeId")
    @Mapping(source = "convertionType.name", target = "convertionTypeName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CConvertionRateDTO toDto(CConvertionRate cConvertionRate);

    @Mapping(source = "sourceCurrencyId", target = "sourceCurrency")
    @Mapping(source = "targetCurrencyId", target = "targetCurrency")
    @Mapping(source = "convertionTypeId", target = "convertionType")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CConvertionRate toEntity(CConvertionRateDTO cConvertionRateDTO);

    default CConvertionRate fromId(Long id) {
        if (id == null) {
            return null;
        }
        CConvertionRate cConvertionRate = new CConvertionRate();
        cConvertionRate.setId(id);
        return cConvertionRate;
    }
}
