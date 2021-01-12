package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPurchaseOrderMapperTest {

    private MPurchaseOrderMapper mPurchaseOrderMapper;

    @BeforeEach
    public void setUp() {
        mPurchaseOrderMapper = new MPurchaseOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPurchaseOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPurchaseOrderMapper.fromId(null)).isNull();
    }
}
