package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBlacklistMapperTest {

    private MBlacklistMapper mBlacklistMapper;

    @BeforeEach
    public void setUp() {
        mBlacklistMapper = new MBlacklistMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBlacklistMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBlacklistMapper.fromId(null)).isNull();
    }
}
