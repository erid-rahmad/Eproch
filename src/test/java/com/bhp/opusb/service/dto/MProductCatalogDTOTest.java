package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProductCatalogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProductCatalogDTO.class);
        MProductCatalogDTO mProductCatalogDTO1 = new MProductCatalogDTO();
        mProductCatalogDTO1.setId(1L);
        MProductCatalogDTO mProductCatalogDTO2 = new MProductCatalogDTO();
        assertThat(mProductCatalogDTO1).isNotEqualTo(mProductCatalogDTO2);
        mProductCatalogDTO2.setId(mProductCatalogDTO1.getId());
        assertThat(mProductCatalogDTO1).isEqualTo(mProductCatalogDTO2);
        mProductCatalogDTO2.setId(2L);
        assertThat(mProductCatalogDTO1).isNotEqualTo(mProductCatalogDTO2);
        mProductCatalogDTO1.setId(null);
        assertThat(mProductCatalogDTO1).isNotEqualTo(mProductCatalogDTO2);
    }
}
