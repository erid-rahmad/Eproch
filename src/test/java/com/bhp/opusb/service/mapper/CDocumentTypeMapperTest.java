package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CDocumentTypeMapperTest {

    private CDocumentTypeMapper cDocumentTypeMapper;

    @BeforeEach
    public void setUp() {
        cDocumentTypeMapper = new CDocumentTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cDocumentTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cDocumentTypeMapper.fromId(null)).isNull();
    }
}
