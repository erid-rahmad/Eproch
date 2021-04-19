package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorScoringCriteriaMapperTest {

    private MVendorScoringCriteriaMapper mVendorScoringCriteriaMapper;

    @BeforeEach
    public void setUp() {
        mVendorScoringCriteriaMapper = new MVendorScoringCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorScoringCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorScoringCriteriaMapper.fromId(null)).isNull();
    }
}
