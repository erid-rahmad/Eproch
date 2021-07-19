package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalAnnouncementMapperTest {

    private MPrequalAnnouncementMapper mPrequalAnnouncementMapper;

    @BeforeEach
    public void setUp() {
        mPrequalAnnouncementMapper = new MPrequalAnnouncementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalAnnouncementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalAnnouncementMapper.fromId(null)).isNull();
    }
}
