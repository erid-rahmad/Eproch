package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CQuestionCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CQuestionCategoryDTO.class);
        CQuestionCategoryDTO cQuestionCategoryDTO1 = new CQuestionCategoryDTO();
        cQuestionCategoryDTO1.setId(1L);
        CQuestionCategoryDTO cQuestionCategoryDTO2 = new CQuestionCategoryDTO();
        assertThat(cQuestionCategoryDTO1).isNotEqualTo(cQuestionCategoryDTO2);
        cQuestionCategoryDTO2.setId(cQuestionCategoryDTO1.getId());
        assertThat(cQuestionCategoryDTO1).isEqualTo(cQuestionCategoryDTO2);
        cQuestionCategoryDTO2.setId(2L);
        assertThat(cQuestionCategoryDTO1).isNotEqualTo(cQuestionCategoryDTO2);
        cQuestionCategoryDTO1.setId(null);
        assertThat(cQuestionCategoryDTO1).isNotEqualTo(cQuestionCategoryDTO2);
    }
}
