package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVerificationLineMapperTest {

    private MVerificationLineMapper mVerificationLineMapper;

    @BeforeEach
    public void setUp() {
        mVerificationLineMapper = new MVerificationLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVerificationLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVerificationLineMapper.fromId(null)).isNull();
    }
}
