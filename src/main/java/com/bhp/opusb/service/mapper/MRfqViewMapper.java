package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MRfqViewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MRfqView} and its DTO {@link MRfqViewDTO}.
 */
@Mapper(componentModel = "spring", uses = {MRfqMapper.class})
public interface MRfqViewMapper extends EntityMapper<MRfqViewDTO, MRfqView> {

    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "quotation.dateTrx", target = "dateTrx")
    @Mapping(source = "quotation.grandTotal", target = "grandTotal")
    MRfqViewDTO toDto(MRfqView mRfqView);

    @Mapping(source = "quotationId", target = "quotation")
    MRfqView toEntity(MRfqViewDTO mRfqViewDTO);

    default MRfqView fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRfqView mRfqView = new MRfqView();
        mRfqView.setId(id);
        return mRfqView;
    }
}
