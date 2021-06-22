package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorEvaluationMapperTest {

    private MVendorEvaluationMapper mVendorEvaluationMapper;

    @BeforeEach
    public void setUp() {
        mVendorEvaluationMapper = new MVendorEvaluationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorEvaluationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorEvaluationMapper.fromId(null)).isNull();
    }
}
