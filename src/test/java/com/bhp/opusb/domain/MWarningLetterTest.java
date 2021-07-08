package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MWarningLetterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MWarningLetter.class);
        MWarningLetter mWarningLetter1 = new MWarningLetter();
        mWarningLetter1.setId(1L);
        MWarningLetter mWarningLetter2 = new MWarningLetter();
        mWarningLetter2.setId(mWarningLetter1.getId());
        assertThat(mWarningLetter1).isEqualTo(mWarningLetter2);
        mWarningLetter2.setId(2L);
        assertThat(mWarningLetter1).isNotEqualTo(mWarningLetter2);
        mWarningLetter1.setId(null);
        assertThat(mWarningLetter1).isNotEqualTo(mWarningLetter2);
    }
}
