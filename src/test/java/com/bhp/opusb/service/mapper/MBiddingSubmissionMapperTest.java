package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingSubmissionMapperTest {

    private MBiddingSubmissionMapper mBiddingSubmissionMapper;

    @BeforeEach
    public void setUp() {
        mBiddingSubmissionMapper = new MBiddingSubmissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingSubmissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingSubmissionMapper.fromId(null)).isNull();
    }
}
