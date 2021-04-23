package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingInvitationMapperTest {

    private MBiddingInvitationMapper mBiddingInvitationMapper;

    @BeforeEach
    public void setUp() {
        mBiddingInvitationMapper = new MBiddingInvitationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingInvitationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingInvitationMapper.fromId(null)).isNull();
    }
}
