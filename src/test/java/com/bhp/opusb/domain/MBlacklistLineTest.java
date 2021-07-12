package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBlacklistLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBlacklistLine.class);
        MBlacklistLine mBlacklistLine1 = new MBlacklistLine();
        mBlacklistLine1.setId(1L);
        MBlacklistLine mBlacklistLine2 = new MBlacklistLine();
        mBlacklistLine2.setId(mBlacklistLine1.getId());
        assertThat(mBlacklistLine1).isEqualTo(mBlacklistLine2);
        mBlacklistLine2.setId(2L);
        assertThat(mBlacklistLine1).isNotEqualTo(mBlacklistLine2);
        mBlacklistLine1.setId(null);
        assertThat(mBlacklistLine1).isNotEqualTo(mBlacklistLine2);
    }
}
