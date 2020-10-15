package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADOrganizationInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADOrganizationInfo.class);
        ADOrganizationInfo aDOrganizationInfo1 = new ADOrganizationInfo();
        aDOrganizationInfo1.setId(1L);
        ADOrganizationInfo aDOrganizationInfo2 = new ADOrganizationInfo();
        aDOrganizationInfo2.setId(aDOrganizationInfo1.getId());
        assertThat(aDOrganizationInfo1).isEqualTo(aDOrganizationInfo2);
        aDOrganizationInfo2.setId(2L);
        assertThat(aDOrganizationInfo1).isNotEqualTo(aDOrganizationInfo2);
        aDOrganizationInfo1.setId(null);
        assertThat(aDOrganizationInfo1).isNotEqualTo(aDOrganizationInfo2);
    }
}
