package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADOrganizationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADOrganization.class);
        ADOrganization aDOrganization1 = new ADOrganization();
        aDOrganization1.setId(1L);
        ADOrganization aDOrganization2 = new ADOrganization();
        aDOrganization2.setId(aDOrganization1.getId());
        assertThat(aDOrganization1).isEqualTo(aDOrganization2);
        aDOrganization2.setId(2L);
        assertThat(aDOrganization1).isNotEqualTo(aDOrganization2);
        aDOrganization1.setId(null);
        assertThat(aDOrganization1).isNotEqualTo(aDOrganization2);
    }
}
