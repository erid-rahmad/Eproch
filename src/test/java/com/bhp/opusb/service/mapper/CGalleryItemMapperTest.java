package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CGalleryItemMapperTest {

    private CGalleryItemMapper cGalleryItemMapper;

    @BeforeEach
    public void setUp() {
        cGalleryItemMapper = new CGalleryItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cGalleryItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cGalleryItemMapper.fromId(null)).isNull();
    }
}
