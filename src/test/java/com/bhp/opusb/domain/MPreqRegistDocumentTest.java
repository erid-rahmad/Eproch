package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreqRegistDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreqRegistDocument.class);
        MPreqRegistDocument mPreqRegistDocument1 = new MPreqRegistDocument();
        mPreqRegistDocument1.setId(1L);
        MPreqRegistDocument mPreqRegistDocument2 = new MPreqRegistDocument();
        mPreqRegistDocument2.setId(mPreqRegistDocument1.getId());
        assertThat(mPreqRegistDocument1).isEqualTo(mPreqRegistDocument2);
        mPreqRegistDocument2.setId(2L);
        assertThat(mPreqRegistDocument1).isNotEqualTo(mPreqRegistDocument2);
        mPreqRegistDocument1.setId(null);
        assertThat(mPreqRegistDocument1).isNotEqualTo(mPreqRegistDocument2);
    }
}
