package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractLine} and its DTO {@link MContractLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MContractMapper.class, ADOrganizationMapper.class, CCostCenterMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class})
public interface MContractLineMapper extends EntityMapper<MContractLineDTO, MContractLine> {

    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "contract.vendor.id", target = "vendorId")
    @Mapping(source = "contract.documentNo", target = "contractNo")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")

    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomCode")
    MContractLineDTO toDto(MContractLine mContractLine);

    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    MContractLine toEntity(MContractLineDTO mContractLineDTO);

    default MContractLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractLine mContractLine = new MContractLine();
        mContractLine.setId(id);
        return mContractLine;
    }
}
