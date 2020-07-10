package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegDocTypeBusinessCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegDocTypeBusinessCategoryDTO.class);
        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO1 = new CRegDocTypeBusinessCategoryDTO();
        cRegDocTypeBusinessCategoryDTO1.setId(1L);
        CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO2 = new CRegDocTypeBusinessCategoryDTO();
        assertThat(cRegDocTypeBusinessCategoryDTO1).isNotEqualTo(cRegDocTypeBusinessCategoryDTO2);
        cRegDocTypeBusinessCategoryDTO2.setId(cRegDocTypeBusinessCategoryDTO1.getId());
        assertThat(cRegDocTypeBusinessCategoryDTO1).isEqualTo(cRegDocTypeBusinessCategoryDTO2);
        cRegDocTypeBusinessCategoryDTO2.setId(2L);
        assertThat(cRegDocTypeBusinessCategoryDTO1).isNotEqualTo(cRegDocTypeBusinessCategoryDTO2);
        cRegDocTypeBusinessCategoryDTO1.setId(null);
        assertThat(cRegDocTypeBusinessCategoryDTO1).isNotEqualTo(cRegDocTypeBusinessCategoryDTO2);
    }
}
