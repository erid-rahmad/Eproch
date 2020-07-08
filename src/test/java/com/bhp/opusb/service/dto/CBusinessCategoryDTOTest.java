package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBusinessCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBusinessCategoryDTO.class);
        CBusinessCategoryDTO cBusinessCategoryDTO1 = new CBusinessCategoryDTO();
        cBusinessCategoryDTO1.setId(1L);
        CBusinessCategoryDTO cBusinessCategoryDTO2 = new CBusinessCategoryDTO();
        assertThat(cBusinessCategoryDTO1).isNotEqualTo(cBusinessCategoryDTO2);
        cBusinessCategoryDTO2.setId(cBusinessCategoryDTO1.getId());
        assertThat(cBusinessCategoryDTO1).isEqualTo(cBusinessCategoryDTO2);
        cBusinessCategoryDTO2.setId(2L);
        assertThat(cBusinessCategoryDTO1).isNotEqualTo(cBusinessCategoryDTO2);
        cBusinessCategoryDTO1.setId(null);
        assertThat(cBusinessCategoryDTO1).isNotEqualTo(cBusinessCategoryDTO2);
    }
}
