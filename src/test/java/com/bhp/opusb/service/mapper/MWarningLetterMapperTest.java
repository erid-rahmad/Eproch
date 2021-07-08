package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MWarningLetterMapperTest {

    private MWarningLetterMapper mWarningLetterMapper;

    @BeforeEach
    public void setUp() {
        mWarningLetterMapper = new MWarningLetterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mWarningLetterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mWarningLetterMapper.fromId(null)).isNull();
    }
}
