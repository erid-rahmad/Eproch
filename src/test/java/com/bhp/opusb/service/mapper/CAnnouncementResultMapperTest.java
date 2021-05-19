package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CAnnouncementResultMapperTest {

    private CAnnouncementResultMapper cAnnouncementResultMapper;

    @BeforeEach
    public void setUp() {
        cAnnouncementResultMapper = new CAnnouncementResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cAnnouncementResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cAnnouncementResultMapper.fromId(null)).isNull();
    }
}
