package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CAnnouncementMapperTest {

    private CAnnouncementMapper cAnnouncementMapper;

    @BeforeEach
    public void setUp() {
        cAnnouncementMapper = new CAnnouncementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cAnnouncementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cAnnouncementMapper.fromId(null)).isNull();
    }
}
