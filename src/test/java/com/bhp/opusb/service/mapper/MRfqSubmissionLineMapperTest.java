package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRfqSubmissionLineMapperTest {

    private MRfqSubmissionLineMapper mRfqSubmissionLineMapper;

    @BeforeEach
    public void setUp() {
        mRfqSubmissionLineMapper = new MRfqSubmissionLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRfqSubmissionLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRfqSubmissionLineMapper.fromId(null)).isNull();
    }
}
