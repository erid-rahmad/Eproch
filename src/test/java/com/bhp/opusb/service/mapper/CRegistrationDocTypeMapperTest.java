package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CRegistrationDocTypeMapperTest {

    private CRegistrationDocTypeMapper cRegistrationDocTypeMapper;

    @BeforeEach
    public void setUp() {
        cRegistrationDocTypeMapper = new CRegistrationDocTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cRegistrationDocTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cRegistrationDocTypeMapper.fromId(null)).isNull();
    }
}
