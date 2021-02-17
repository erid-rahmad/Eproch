package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingMapperTest {

    private MBiddingMapper mBiddingMapper;

    @BeforeEach
    public void setUp() {
        mBiddingMapper = new MBiddingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingMapper.fromId(null)).isNull();
    }
}
