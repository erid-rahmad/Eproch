package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorScoringLineMapperTest {

    private MVendorScoringLineMapper mVendorScoringLineMapper;

    @BeforeEach
    public void setUp() {
        mVendorScoringLineMapper = new MVendorScoringLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorScoringLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorScoringLineMapper.fromId(null)).isNull();
    }
}
