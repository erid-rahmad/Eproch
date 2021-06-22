package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorEvaluationLineMapperTest {

    private MVendorEvaluationLineMapper mVendorEvaluationLineMapper;

    @BeforeEach
    public void setUp() {
        mVendorEvaluationLineMapper = new MVendorEvaluationLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorEvaluationLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorEvaluationLineMapper.fromId(null)).isNull();
    }
}
