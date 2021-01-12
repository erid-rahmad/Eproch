package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPurchaseOrderLineMapperTest {

    private MPurchaseOrderLineMapper mPurchaseOrderLineMapper;

    @BeforeEach
    public void setUp() {
        mPurchaseOrderLineMapper = new MPurchaseOrderLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPurchaseOrderLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPurchaseOrderLineMapper.fromId(null)).isNull();
    }
}
