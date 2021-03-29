package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorEvaluationMapperTest {

    private CVendorEvaluationMapper cVendorEvaluationMapper;

    @BeforeEach
    public void setUp() {
        cVendorEvaluationMapper = new CVendorEvaluationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorEvaluationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorEvaluationMapper.fromId(null)).isNull();
    }
}
