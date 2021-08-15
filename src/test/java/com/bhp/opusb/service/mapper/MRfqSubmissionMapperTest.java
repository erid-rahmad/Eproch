package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRfqSubmissionMapperTest {

    private MRfqSubmissionMapper mRfqSubmissionMapper;

    @BeforeEach
    public void setUp() {
        mRfqSubmissionMapper = new MRfqSubmissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRfqSubmissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRfqSubmissionMapper.fromId(null)).isNull();
    }
}
