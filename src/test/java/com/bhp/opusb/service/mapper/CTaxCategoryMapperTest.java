package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CTaxCategoryMapperTest {

    private CTaxCategoryMapper cTaxCategoryMapper;

    @BeforeEach
    public void setUp() {
        cTaxCategoryMapper = new CTaxCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cTaxCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cTaxCategoryMapper.fromId(null)).isNull();
    }
}
