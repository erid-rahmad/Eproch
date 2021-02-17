package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MDocumentScheduleMapperTest {

    private MDocumentScheduleMapper mDocumentScheduleMapper;

    @BeforeEach
    public void setUp() {
        mDocumentScheduleMapper = new MDocumentScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mDocumentScheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mDocumentScheduleMapper.fromId(null)).isNull();
    }
}
