package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MQuoteSupplierDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MQuoteSupplierDTO.class);
        MQuoteSupplierDTO mQuoteSupplierDTO1 = new MQuoteSupplierDTO();
        mQuoteSupplierDTO1.setId(1L);
        MQuoteSupplierDTO mQuoteSupplierDTO2 = new MQuoteSupplierDTO();
        assertThat(mQuoteSupplierDTO1).isNotEqualTo(mQuoteSupplierDTO2);
        mQuoteSupplierDTO2.setId(mQuoteSupplierDTO1.getId());
        assertThat(mQuoteSupplierDTO1).isEqualTo(mQuoteSupplierDTO2);
        mQuoteSupplierDTO2.setId(2L);
        assertThat(mQuoteSupplierDTO1).isNotEqualTo(mQuoteSupplierDTO2);
        mQuoteSupplierDTO1.setId(null);
        assertThat(mQuoteSupplierDTO1).isNotEqualTo(mQuoteSupplierDTO2);
    }
}
