package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionSubmissionItemMapperTest {

    private MAuctionSubmissionItemMapper mAuctionSubmissionItemMapper;

    @BeforeEach
    public void setUp() {
        mAuctionSubmissionItemMapper = new MAuctionSubmissionItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionSubmissionItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionSubmissionItemMapper.fromId(null)).isNull();
    }
}
