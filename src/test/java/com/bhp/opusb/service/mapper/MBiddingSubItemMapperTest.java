package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingSubItemMapperTest {

    private MBiddingSubItemMapper mBiddingSubItemMapper;

    @BeforeEach
    public void setUp() {
        mBiddingSubItemMapper = new MBiddingSubItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingSubItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingSubItemMapper.fromId(null)).isNull();
    }
}
