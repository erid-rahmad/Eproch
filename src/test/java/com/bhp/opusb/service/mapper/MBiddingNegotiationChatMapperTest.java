package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingNegotiationChatMapperTest {

    private MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper;

    @BeforeEach
    public void setUp() {
        mBiddingNegotiationChatMapper = new MBiddingNegotiationChatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingNegotiationChatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingNegotiationChatMapper.fromId(null)).isNull();
    }
}
