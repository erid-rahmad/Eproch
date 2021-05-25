package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionContentMapperTest {

    private MAuctionContentMapper mAuctionContentMapper;

    @BeforeEach
    public void setUp() {
        mAuctionContentMapper = new MAuctionContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionContentMapper.fromId(null)).isNull();
    }
}
