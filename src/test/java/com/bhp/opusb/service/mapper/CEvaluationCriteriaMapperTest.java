package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEvaluationCriteriaMapperTest {

    private CEvaluationCriteriaMapper cEvaluationCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cEvaluationCriteriaMapper = new CEvaluationCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEvaluationCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEvaluationCriteriaMapper.fromId(null)).isNull();
    }
}
