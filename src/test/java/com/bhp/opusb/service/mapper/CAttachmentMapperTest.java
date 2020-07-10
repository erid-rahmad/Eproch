package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CAttachmentMapperTest {

    private CAttachmentMapper cAttachmentMapper;

    @BeforeEach
    public void setUp() {
        cAttachmentMapper = new CAttachmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cAttachmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cAttachmentMapper.fromId(null)).isNull();
    }
}
