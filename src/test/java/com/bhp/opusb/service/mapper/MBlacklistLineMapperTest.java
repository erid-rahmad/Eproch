package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MBlacklistLineMapperTest {

    private MBlacklistLineMapper mBlacklistLineMapper;

    @BeforeEach
    public void setUp() {
        mBlacklistLineMapper = new MBlacklistLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mBlacklistLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mBlacklistLineMapper.fromId(null)).isNull();
    }
}
