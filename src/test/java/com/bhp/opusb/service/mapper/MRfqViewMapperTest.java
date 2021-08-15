package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRfqViewMapperTest {

    private MRfqViewMapper mRfqViewMapper;

    @BeforeEach
    public void setUp() {
        mRfqViewMapper = new MRfqViewMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRfqViewMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRfqViewMapper.fromId(null)).isNull();
    }
}
