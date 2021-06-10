package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionInvitationMapperTest {

    private MAuctionInvitationMapper mAuctionInvitationMapper;

    @BeforeEach
    public void setUp() {
        mAuctionInvitationMapper = new MAuctionInvitationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionInvitationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionInvitationMapper.fromId(null)).isNull();
    }
}
