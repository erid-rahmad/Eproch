package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CGalleryMapperTest {

    private CGalleryMapper cGalleryMapper;

    @BeforeEach
    public void setUp() {
        cGalleryMapper = new CGalleryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cGalleryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cGalleryMapper.fromId(null)).isNull();
    }
}
