package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MComplaintMapperTest {

    private MComplaintMapper mComplaintMapper;

    @BeforeEach
    public void setUp() {
        mComplaintMapper = new MComplaintMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mComplaintMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mComplaintMapper.fromId(null)).isNull();
    }
}
