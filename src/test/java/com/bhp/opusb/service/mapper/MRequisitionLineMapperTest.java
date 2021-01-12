package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MRequisitionLineMapperTest {

    private MRequisitionLineMapper mRequisitionLineMapper;

    @BeforeEach
    public void setUp() {
        mRequisitionLineMapper = new MRequisitionLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mRequisitionLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mRequisitionLineMapper.fromId(null)).isNull();
    }
}
