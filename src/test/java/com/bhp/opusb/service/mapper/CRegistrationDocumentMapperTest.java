package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CRegistrationDocumentMapperTest {

    private CRegistrationDocumentMapper cRegistrationDocumentMapper;

    @BeforeEach
    public void setUp() {
        cRegistrationDocumentMapper = new CRegistrationDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cRegistrationDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cRegistrationDocumentMapper.fromId(null)).isNull();
    }
}
