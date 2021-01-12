package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRequisitionMapperTest {

    private MRequisitionMapper mRequisitionMapper;

    @BeforeEach
    public void setUp() {
        mRequisitionMapper = new MRequisitionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRequisitionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRequisitionMapper.fromId(null)).isNull();
    }
}
