package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MQuoteSupplierMapperTest {

    private MQuoteSupplierMapper mQuoteSupplierMapper;

    @BeforeEach
    public void setUp() {
        mQuoteSupplierMapper = new MQuoteSupplierMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mQuoteSupplierMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mQuoteSupplierMapper.fromId(null)).isNull();
    }
}
