package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationCriteriaMapperTest {

    private MPrequalificationCriteriaMapper mPrequalificationCriteriaMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationCriteriaMapper = new MPrequalificationCriteriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationCriteriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationCriteriaMapper.fromId(null)).isNull();
    }
}
