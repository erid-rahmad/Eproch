package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEvaluationMethodCriteriaMapperTest {

    private CEvaluationMethodCriteriaMapper cEvaluationMethodCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cEvaluationMethodCriteriaMapper = new CEvaluationMethodCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEvaluationMethodCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEvaluationMethodCriteriaMapper.fromId(null)).isNull();
    }
}
