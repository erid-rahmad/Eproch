package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationEvalFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationEvalFile.class);
        MPrequalificationEvalFile mPrequalificationEvalFile1 = new MPrequalificationEvalFile();
        mPrequalificationEvalFile1.setId(1L);
        MPrequalificationEvalFile mPrequalificationEvalFile2 = new MPrequalificationEvalFile();
        mPrequalificationEvalFile2.setId(mPrequalificationEvalFile1.getId());
        assertThat(mPrequalificationEvalFile1).isEqualTo(mPrequalificationEvalFile2);
        mPrequalificationEvalFile2.setId(2L);
        assertThat(mPrequalificationEvalFile1).isNotEqualTo(mPrequalificationEvalFile2);
        mPrequalificationEvalFile1.setId(null);
        assertThat(mPrequalificationEvalFile1).isNotEqualTo(mPrequalificationEvalFile2);
    }
}
