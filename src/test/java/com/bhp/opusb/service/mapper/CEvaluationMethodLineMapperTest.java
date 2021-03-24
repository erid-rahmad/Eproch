package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEvaluationMethodLineMapperTest {

    private CEvaluationMethodLineMapper cEvaluationMethodLineMapper;

    @BeforeEach
    public void setUp() {
        cEvaluationMethodLineMapper = new CEvaluationMethodLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEvaluationMethodLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEvaluationMethodLineMapper.fromId(null)).isNull();
    }
}
