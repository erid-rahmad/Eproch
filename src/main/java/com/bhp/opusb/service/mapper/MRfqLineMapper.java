package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MRfqLine;
import com.bhp.opusb.service.dto.MRfqLineDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MRfqLine} and its DTO {@link MRfqLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MRfqMapper.class, ADOrganizationMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class, CBusinessCategoryMapper.class})
public interface MRfqLineMapper extends EntityMapper<MRfqLineDTO, MRfqLine> {

    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "quotation.documentNo", target = "quotationName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.code", target = "uomName")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    MRfqLineDTO toDto(MRfqLine mRfqLine);

    @Mapping(source = "quotationId", target = "quotation")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    MRfqLine toEntity(MRfqLineDTO mRfqLineDTO);

    default MRfqLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRfqLine mRfqLine = new MRfqLine();
        mRfqLine.setId(id);
        return mRfqLine;
    }
}
