package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionItemMapperTest {

    private MAuctionItemMapper mAuctionItemMapper;

    @BeforeEach
    public void setUp() {
        mAuctionItemMapper = new MAuctionItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionItemMapper.fromId(null)).isNull();
    }
}
