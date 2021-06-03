package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingNegotiationLineMapperTest {

    private MBiddingNegotiationLineMapper mBiddingNegotiationLineMapper;

    @BeforeEach
    public void setUp() {
        mBiddingNegotiationLineMapper = new MBiddingNegotiationLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingNegotiationLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingNegotiationLineMapper.fromId(null)).isNull();
    }
}
