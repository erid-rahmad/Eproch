package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBiddingSubItemLineMapperTest {

    private MBiddingSubItemLineMapper mBiddingSubItemLineMapper;

    @BeforeEach
    public void setUp() {
        mBiddingSubItemLineMapper = new MBiddingSubItemLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBiddingSubItemLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBiddingSubItemLineMapper.fromId(null)).isNull();
    }
}
