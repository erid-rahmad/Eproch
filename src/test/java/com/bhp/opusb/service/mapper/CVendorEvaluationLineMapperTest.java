package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CVendorEvaluationLineMapperTest {

    private CVendorEvaluationLineMapper cVendorEvaluationLineMapper;

    @BeforeEach
    public void setUp() {
        cVendorEvaluationLineMapper = new CVendorEvaluationLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cVendorEvaluationLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cVendorEvaluationLineMapper.fromId(null)).isNull();
    }
}
