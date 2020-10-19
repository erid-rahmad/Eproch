package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MMatchPOMapperTest {

    private MMatchPOMapper mMatchPOMapper;

    @BeforeEach
    public void setUp() {
        mMatchPOMapper = new MMatchPOMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mMatchPOMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mMatchPOMapper.fromId(null)).isNull();
    }
}
