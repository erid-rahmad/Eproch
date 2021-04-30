package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPreBidMeetingMapperTest {

    private MPreBidMeetingMapper mPreBidMeetingMapper;

    @BeforeEach
    public void setUp() {
        mPreBidMeetingMapper = new MPreBidMeetingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPreBidMeetingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPreBidMeetingMapper.fromId(null)).isNull();
    }
}
