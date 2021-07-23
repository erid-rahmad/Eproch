package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationEvalMapperTest {

    private MPrequalificationEvalMapper mPrequalificationEvalMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationEvalMapper = new MPrequalificationEvalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationEvalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationEvalMapper.fromId(null)).isNull();
    }
}
