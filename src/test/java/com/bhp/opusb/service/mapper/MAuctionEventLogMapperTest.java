package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionEventLogMapperTest {

    private MAuctionEventLogMapper mAuctionEventLogMapper;

    @BeforeEach
    public void setUp() {
        mAuctionEventLogMapper = new MAuctionEventLogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionEventLogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionEventLogMapper.fromId(null)).isNull();
    }
}
