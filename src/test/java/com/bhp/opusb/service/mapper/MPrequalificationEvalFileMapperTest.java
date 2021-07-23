package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationEvalFileMapperTest {

    private MPrequalificationEvalFileMapper mPrequalificationEvalFileMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationEvalFileMapper = new MPrequalificationEvalFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationEvalFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationEvalFileMapper.fromId(null)).isNull();
    }
}
