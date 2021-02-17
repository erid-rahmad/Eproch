package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingScheduleMapperTest {

    private MBiddingScheduleMapper mBiddingScheduleMapper;

    @BeforeEach
    public void setUp() {
        mBiddingScheduleMapper = new MBiddingScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingScheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingScheduleMapper.fromId(null)).isNull();
    }
}
