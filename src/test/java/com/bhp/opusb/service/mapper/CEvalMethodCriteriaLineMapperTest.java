package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEvalMethodCriteriaLineMapperTest {

    private CEvalMethodCriteriaLineMapper cEvalMethodCriteriaLineMapper;

    @BeforeEach
    public void setUp() {
        cEvalMethodCriteriaLineMapper = new CEvalMethodCriteriaLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEvalMethodCriteriaLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEvalMethodCriteriaLineMapper.fromId(null)).isNull();
    }
}
