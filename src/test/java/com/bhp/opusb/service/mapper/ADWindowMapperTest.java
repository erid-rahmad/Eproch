package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADWindowMapperTest {

    private ADWindowMapper aDWindowMapper;

    @BeforeEach
    public void setUp() {
        aDWindowMapper = new ADWindowMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDWindowMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDWindowMapper.fromId(null)).isNull();
    }
}
