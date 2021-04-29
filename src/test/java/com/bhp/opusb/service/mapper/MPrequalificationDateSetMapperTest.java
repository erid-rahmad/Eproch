package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalificationDateSetMapperTest {

    private MPrequalificationDateSetMapper mPrequalificationDateSetMapper;

    @BeforeEach
    public void setUp() {
        mPrequalificationDateSetMapper = new MPrequalificationDateSetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalificationDateSetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalificationDateSetMapper.fromId(null)).isNull();
    }
}
