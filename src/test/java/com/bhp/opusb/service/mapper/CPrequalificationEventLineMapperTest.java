package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPrequalificationEventLineMapperTest {

    private CPrequalificationEventLineMapper cPrequalificationEventLineMapper;

    @BeforeEach
    public void setUp() {
        cPrequalificationEventLineMapper = new CPrequalificationEventLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPrequalificationEventLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPrequalificationEventLineMapper.fromId(null)).isNull();
    }
}
