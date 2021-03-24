package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingScheduleAttachmentMapperTest {

    private MBiddingScheduleAttachmentMapper mBiddingScheduleAttachmentMapper;

    @BeforeEach
    public void setUp() {
        mBiddingScheduleAttachmentMapper = new MBiddingScheduleAttachmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingScheduleAttachmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingScheduleAttachmentMapper.fromId(null)).isNull();
    }
}
