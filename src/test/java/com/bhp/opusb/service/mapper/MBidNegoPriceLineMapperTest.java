package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBidNegoPriceLineMapperTest {

    private MBidNegoPriceLineMapper mBidNegoPriceLineMapper;

    @BeforeEach
    public void setUp() {
        mBidNegoPriceLineMapper = new MBidNegoPriceLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBidNegoPriceLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBidNegoPriceLineMapper.fromId(null)).isNull();
    }
}
