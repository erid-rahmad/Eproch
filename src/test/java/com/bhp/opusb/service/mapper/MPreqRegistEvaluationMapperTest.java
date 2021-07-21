package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPreqRegistEvaluationMapperTest {

    private MPreqRegistEvaluationMapper mPreqRegistEvaluationMapper;

    @BeforeEach
    public void setUp() {
        mPreqRegistEvaluationMapper = new MPreqRegistEvaluationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPreqRegistEvaluationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPreqRegistEvaluationMapper.fromId(null)).isNull();
    }
}
