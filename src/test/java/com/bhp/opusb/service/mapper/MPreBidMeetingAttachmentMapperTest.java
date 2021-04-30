package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPreBidMeetingAttachmentMapperTest {

    private MPreBidMeetingAttachmentMapper mPreBidMeetingAttachmentMapper;

    @BeforeEach
    public void setUp() {
        mPreBidMeetingAttachmentMapper = new MPreBidMeetingAttachmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPreBidMeetingAttachmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPreBidMeetingAttachmentMapper.fromId(null)).isNull();
    }
}
