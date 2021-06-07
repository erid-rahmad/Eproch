package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MAuctionRuleMapperTest {

    private MAuctionRuleMapper mAuctionRuleMapper;

    @BeforeEach
    public void setUp() {
        mAuctionRuleMapper = new MAuctionRuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mAuctionRuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mAuctionRuleMapper.fromId(null)).isNull();
    }
}
