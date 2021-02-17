package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProjectInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProjectInformation.class);
        MProjectInformation mProjectInformation1 = new MProjectInformation();
        mProjectInformation1.setId(1L);
        MProjectInformation mProjectInformation2 = new MProjectInformation();
        mProjectInformation2.setId(mProjectInformation1.getId());
        assertThat(mProjectInformation1).isEqualTo(mProjectInformation2);
        mProjectInformation2.setId(2L);
        assertThat(mProjectInformation1).isNotEqualTo(mProjectInformation2);
        mProjectInformation1.setId(null);
        assertThat(mProjectInformation1).isNotEqualTo(mProjectInformation2);
    }
}
