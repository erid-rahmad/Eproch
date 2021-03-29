package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CQuestionCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CQuestionCategory.class);
        CQuestionCategory cQuestionCategory1 = new CQuestionCategory();
        cQuestionCategory1.setId(1L);
        CQuestionCategory cQuestionCategory2 = new CQuestionCategory();
        cQuestionCategory2.setId(cQuestionCategory1.getId());
        assertThat(cQuestionCategory1).isEqualTo(cQuestionCategory2);
        cQuestionCategory2.setId(2L);
        assertThat(cQuestionCategory1).isNotEqualTo(cQuestionCategory2);
        cQuestionCategory1.setId(null);
        assertThat(cQuestionCategory1).isNotEqualTo(cQuestionCategory2);
    }
}
