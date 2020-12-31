package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProductPriceMapperTest {

    private MProductPriceMapper mProductPriceMapper;

    @BeforeEach
    public void setUp() {
        mProductPriceMapper = new MProductPriceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProductPriceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProductPriceMapper.fromId(null)).isNull();
    }
}
