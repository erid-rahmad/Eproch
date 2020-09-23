package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class COrganizationInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(COrganizationInfo.class);
        COrganizationInfo cOrganizationInfo1 = new COrganizationInfo();
        cOrganizationInfo1.setId(1L);
        COrganizationInfo cOrganizationInfo2 = new COrganizationInfo();
        cOrganizationInfo2.setId(cOrganizationInfo1.getId());
        assertThat(cOrganizationInfo1).isEqualTo(cOrganizationInfo2);
        cOrganizationInfo2.setId(2L);
        assertThat(cOrganizationInfo1).isNotEqualTo(cOrganizationInfo2);
        cOrganizationInfo1.setId(null);
        assertThat(cOrganizationInfo1).isNotEqualTo(cOrganizationInfo2);
    }
}
