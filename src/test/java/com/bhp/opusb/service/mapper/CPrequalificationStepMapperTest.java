package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPrequalificationStepMapperTest {

    private CPrequalificationStepMapper cPrequalificationStepMapper;

    @BeforeEach
    public void setUp() {
        cPrequalificationStepMapper = new CPrequalificationStepMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPrequalificationStepMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPrequalificationStepMapper.fromId(null)).isNull();
    }
}
