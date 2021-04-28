package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationDateSetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationDateSet.class);
        MPrequalificationDateSet mPrequalificationDateSet1 = new MPrequalificationDateSet();
        mPrequalificationDateSet1.setId(1L);
        MPrequalificationDateSet mPrequalificationDateSet2 = new MPrequalificationDateSet();
        mPrequalificationDateSet2.setId(mPrequalificationDateSet1.getId());
        assertThat(mPrequalificationDateSet1).isEqualTo(mPrequalificationDateSet2);
        mPrequalificationDateSet2.setId(2L);
        assertThat(mPrequalificationDateSet1).isNotEqualTo(mPrequalificationDateSet2);
        mPrequalificationDateSet1.setId(null);
        assertThat(mPrequalificationDateSet1).isNotEqualTo(mPrequalificationDateSet2);
    }
}
