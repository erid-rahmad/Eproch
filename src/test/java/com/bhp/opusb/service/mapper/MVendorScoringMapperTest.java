package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorScoringMapperTest {

    private MVendorScoringMapper mVendorScoringMapper;

    @BeforeEach
    public void setUp() {
        mVendorScoringMapper = new MVendorScoringMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorScoringMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorScoringMapper.fromId(null)).isNull();
    }
}
