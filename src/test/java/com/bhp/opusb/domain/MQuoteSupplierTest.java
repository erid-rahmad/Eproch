package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MQuoteSupplierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MQuoteSupplier.class);
        MQuoteSupplier mQuoteSupplier1 = new MQuoteSupplier();
        mQuoteSupplier1.setId(1L);
        MQuoteSupplier mQuoteSupplier2 = new MQuoteSupplier();
        mQuoteSupplier2.setId(mQuoteSupplier1.getId());
        assertThat(mQuoteSupplier1).isEqualTo(mQuoteSupplier2);
        mQuoteSupplier2.setId(2L);
        assertThat(mQuoteSupplier1).isNotEqualTo(mQuoteSupplier2);
        mQuoteSupplier1.setId(null);
        assertThat(mQuoteSupplier1).isNotEqualTo(mQuoteSupplier2);
    }
}
