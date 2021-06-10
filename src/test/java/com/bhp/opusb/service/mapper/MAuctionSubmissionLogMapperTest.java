package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionSubmissionLogMapperTest {

    private MAuctionSubmissionLogMapper mAuctionSubmissionLogMapper;

    @BeforeEach
    public void setUp() {
        mAuctionSubmissionLogMapper = new MAuctionSubmissionLogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionSubmissionLogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionSubmissionLogMapper.fromId(null)).isNull();
    }
}
