package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionTeamMapperTest {

    private MAuctionTeamMapper mAuctionTeamMapper;

    @BeforeEach
    public void setUp() {
        mAuctionTeamMapper = new MAuctionTeamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionTeamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionTeamMapper.fromId(null)).isNull();
    }
}
