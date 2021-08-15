package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MRfqSubmissionLine} and its DTO {@link MRfqSubmissionLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MRfqSubmissionMapper.class, MRfqLineMapper.class, ADOrganizationMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class, CBusinessCategoryMapper.class})
public interface MRfqSubmissionLineMapper extends EntityMapper<MRfqSubmissionLineDTO, MRfqSubmissionLine> {

    @Mapping(source = "submission.id", target = "submissionId")
    @Mapping(source = "quotationLine.id", target = "quotationLineId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    MRfqSubmissionLineDTO toDto(MRfqSubmissionLine mRfqSubmissionLine);

    @Mapping(source = "submissionId", target = "submission")
    @Mapping(source = "quotationLineId", target = "quotationLine")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    MRfqSubmissionLine toEntity(MRfqSubmissionLineDTO mRfqSubmissionLineDTO);

    default MRfqSubmissionLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRfqSubmissionLine mRfqSubmissionLine = new MRfqSubmissionLine();
        mRfqSubmissionLine.setId(id);
        return mRfqSubmissionLine;
    }
}
