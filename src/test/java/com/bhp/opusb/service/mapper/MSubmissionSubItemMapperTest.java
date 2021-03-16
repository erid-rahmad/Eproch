package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MSubmissionSubItemMapperTest {

    private MSubmissionSubItemMapper mSubmissionSubItemMapper;

    @BeforeEach
    public void setUp() {
        mSubmissionSubItemMapper = new MSubmissionSubItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mSubmissionSubItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mSubmissionSubItemMapper.fromId(null)).isNull();
    }
}
