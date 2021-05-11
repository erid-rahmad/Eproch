package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CPrequalificationEventMapperTest {

    private CPrequalificationEventMapper cPrequalificationEventMapper;

    @BeforeEach
    public void setUp() {
        cPrequalificationEventMapper = new CPrequalificationEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cPrequalificationEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cPrequalificationEventMapper.fromId(null)).isNull();
    }
}
