package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionSubmissionMapperTest {

    private MAuctionSubmissionMapper mAuctionSubmissionMapper;

    @BeforeEach
    public void setUp() {
        mAuctionSubmissionMapper = new MAuctionSubmissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionSubmissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionSubmissionMapper.fromId(null)).isNull();
    }
}
