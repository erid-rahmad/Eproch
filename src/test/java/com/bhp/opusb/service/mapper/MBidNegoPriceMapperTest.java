package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBidNegoPriceMapperTest {

    private MBidNegoPriceMapper mBidNegoPriceMapper;

    @BeforeEach
    public void setUp() {
        mBidNegoPriceMapper = new MBidNegoPriceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBidNegoPriceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBidNegoPriceMapper.fromId(null)).isNull();
    }
}
