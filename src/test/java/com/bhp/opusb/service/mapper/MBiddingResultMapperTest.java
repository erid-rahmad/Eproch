package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingResultMapperTest {

    private MBiddingResultMapper mBiddingResultMapper;

    @BeforeEach
    public void setUp() {
        mBiddingResultMapper = new MBiddingResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingResultMapper.fromId(null)).isNull();
    }
}
