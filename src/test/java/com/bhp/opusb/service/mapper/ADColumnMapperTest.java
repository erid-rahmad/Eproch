package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADColumnMapperTest {

    private ADColumnMapper aDColumnMapper;

    @BeforeEach
    public void setUp() {
        aDColumnMapper = new ADColumnMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDColumnMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDColumnMapper.fromId(null)).isNull();
    }
}
