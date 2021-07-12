package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBlacklistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBlacklist.class);
        MBlacklist mBlacklist1 = new MBlacklist();
        mBlacklist1.setId(1L);
        MBlacklist mBlacklist2 = new MBlacklist();
        mBlacklist2.setId(mBlacklist1.getId());
        assertThat(mBlacklist1).isEqualTo(mBlacklist2);
        mBlacklist2.setId(2L);
        assertThat(mBlacklist1).isNotEqualTo(mBlacklist2);
        mBlacklist1.setId(null);
        assertThat(mBlacklist1).isNotEqualTo(mBlacklist2);
    }
}
