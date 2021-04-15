package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CEvalMethodSubCriteriaMapperTest {

    private CEvalMethodSubCriteriaMapper cEvalMethodSubCriteriaMapper;

    @BeforeEach
    public void setUp() {
        cEvalMethodSubCriteriaMapper = new CEvalMethodSubCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cEvalMethodSubCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cEvalMethodSubCriteriaMapper.fromId(null)).isNull();
    }
}
