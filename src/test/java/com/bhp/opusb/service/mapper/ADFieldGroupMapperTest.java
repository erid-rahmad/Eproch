package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ADFieldGroupMapperTest {

    private ADFieldGroupMapper aDFieldGroupMapper;

    @BeforeEach
    public void setUp() {
        aDFieldGroupMapper = new ADFieldGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aDFieldGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aDFieldGroupMapper.fromId(null)).isNull();
    }
}
