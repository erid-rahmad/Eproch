package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProductCatalogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProductCatalog.class);
        MProductCatalog mProductCatalog1 = new MProductCatalog();
        mProductCatalog1.setId(1L);
        MProductCatalog mProductCatalog2 = new MProductCatalog();
        mProductCatalog2.setId(mProductCatalog1.getId());
        assertThat(mProductCatalog1).isEqualTo(mProductCatalog2);
        mProductCatalog2.setId(2L);
        assertThat(mProductCatalog1).isNotEqualTo(mProductCatalog2);
        mProductCatalog1.setId(null);
        assertThat(mProductCatalog1).isNotEqualTo(mProductCatalog2);
    }
}
