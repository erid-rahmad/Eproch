package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductGroup.class);
        CProductGroup cProductGroup1 = new CProductGroup();
        cProductGroup1.setId(1L);
        CProductGroup cProductGroup2 = new CProductGroup();
        cProductGroup2.setId(cProductGroup1.getId());
        assertThat(cProductGroup1).isEqualTo(cProductGroup2);
        cProductGroup2.setId(2L);
        assertThat(cProductGroup1).isNotEqualTo(cProductGroup2);
        cProductGroup1.setId(null);
        assertThat(cProductGroup1).isNotEqualTo(cProductGroup2);
    }
}
