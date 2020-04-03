package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SupportingDocumentMapperTest {

    private SupportingDocumentMapper supportingDocumentMapper;

    @BeforeEach
    public void setUp() {
        supportingDocumentMapper = new SupportingDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supportingDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supportingDocumentMapper.fromId(null)).isNull();
    }
}
