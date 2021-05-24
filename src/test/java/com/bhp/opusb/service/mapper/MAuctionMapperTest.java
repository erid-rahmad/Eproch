package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionMapperTest {

    private MAuctionMapper mAuctionMapper;

    @BeforeEach
    public void setUp() {
        mAuctionMapper = new MAuctionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionMapper.fromId(null)).isNull();
    }
}
