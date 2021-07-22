package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationSubmissionMapperTest {

    private MPrequalificationSubmissionMapper mPrequalificationSubmissionMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationSubmissionMapper = new MPrequalificationSubmissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationSubmissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationSubmissionMapper.fromId(null)).isNull();
    }
}
