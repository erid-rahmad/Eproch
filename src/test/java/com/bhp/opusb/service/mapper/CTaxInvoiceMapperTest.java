package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CTaxInvoiceMapperTest {

    private CTaxInvoiceMapper cTaxInvoiceMapper;

    @BeforeEach
    public void setUp() {
        cTaxInvoiceMapper = new CTaxInvoiceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cTaxInvoiceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cTaxInvoiceMapper.fromId(null)).isNull();
    }
}
