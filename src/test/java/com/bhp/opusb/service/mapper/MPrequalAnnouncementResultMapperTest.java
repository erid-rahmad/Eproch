package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalAnnouncementResultMapperTest {

    private MPrequalAnnouncementResultMapper mPrequalAnnouncementResultMapper;

    @BeforeEach
    public void setUp() {
        mPrequalAnnouncementResultMapper = new MPrequalAnnouncementResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalAnnouncementResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalAnnouncementResultMapper.fromId(null)).isNull();
    }
}
