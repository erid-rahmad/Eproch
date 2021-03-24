package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEvaluationMethodMapperTest {

    private CEvaluationMethodMapper cEvaluationMethodMapper;

    @BeforeEach
    public void setUp() {
        cEvaluationMethodMapper = new CEvaluationMethodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEvaluationMethodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEvaluationMethodMapper.fromId(null)).isNull();
    }
}
