package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADFieldGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADFieldGroup.class);
        ADFieldGroup aDFieldGroup1 = new ADFieldGroup();
        aDFieldGroup1.setId(1L);
        ADFieldGroup aDFieldGroup2 = new ADFieldGroup();
        aDFieldGroup2.setId(aDFieldGroup1.getId());
        assertThat(aDFieldGroup1).isEqualTo(aDFieldGroup2);
        aDFieldGroup2.setId(2L);
        assertThat(aDFieldGroup1).isNotEqualTo(aDFieldGroup2);
        aDFieldGroup1.setId(null);
        assertThat(aDFieldGroup1).isNotEqualTo(aDFieldGroup2);
    }
}
