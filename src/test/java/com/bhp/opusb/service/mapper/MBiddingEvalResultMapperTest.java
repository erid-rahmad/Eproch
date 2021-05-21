package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingEvalResultMapperTest {

    private MBiddingEvalResultMapper mBiddingEvalResultMapper;

    @BeforeEach
    public void setUp() {
        mBiddingEvalResultMapper = new MBiddingEvalResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingEvalResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingEvalResultMapper.fromId(null)).isNull();
    }
}
