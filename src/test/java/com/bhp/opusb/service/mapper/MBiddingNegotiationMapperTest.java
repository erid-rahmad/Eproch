package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingNegotiationMapperTest {

    private MBiddingNegotiationMapper mBiddingNegotiationMapper;

    @BeforeEach
    public void setUp() {
        mBiddingNegotiationMapper = new MBiddingNegotiationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingNegotiationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingNegotiationMapper.fromId(null)).isNull();
    }
}
