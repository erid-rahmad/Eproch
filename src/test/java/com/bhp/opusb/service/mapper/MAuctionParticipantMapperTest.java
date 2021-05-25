package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionParticipantMapperTest {

    private MAuctionParticipantMapper mAuctionParticipantMapper;

    @BeforeEach
    public void setUp() {
        mAuctionParticipantMapper = new MAuctionParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionParticipantMapper.fromId(null)).isNull();
    }
}
