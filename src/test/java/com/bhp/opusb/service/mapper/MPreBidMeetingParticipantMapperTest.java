package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPreBidMeetingParticipantMapperTest {

    private MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper;

    @BeforeEach
    public void setUp() {
        mPreBidMeetingParticipantMapper = new MPreBidMeetingParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPreBidMeetingParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPreBidMeetingParticipantMapper.fromId(null)).isNull();
    }
}
