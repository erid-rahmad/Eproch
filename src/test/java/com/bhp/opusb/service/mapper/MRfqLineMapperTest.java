package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRfqLineMapperTest {

    private MRfqLineMapper mRfqLineMapper;

    @BeforeEach
    public void setUp() {
        mRfqLineMapper = new MRfqLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRfqLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRfqLineMapper.fromId(null)).isNull();
    }
}
