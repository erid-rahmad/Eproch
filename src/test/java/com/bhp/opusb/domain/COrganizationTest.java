package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class COrganizationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(COrganization.class);
        COrganization cOrganization1 = new COrganization();
        cOrganization1.setId(1L);
        COrganization cOrganization2 = new COrganization();
        cOrganization2.setId(cOrganization1.getId());
        assertThat(cOrganization1).isEqualTo(cOrganization2);
        cOrganization2.setId(2L);
        assertThat(cOrganization1).isNotEqualTo(cOrganization2);
        cOrganization1.setId(null);
        assertThat(cOrganization1).isNotEqualTo(cOrganization2);
    }
}
