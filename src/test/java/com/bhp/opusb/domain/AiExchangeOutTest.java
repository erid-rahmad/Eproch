package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AiExchangeOutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AiExchangeOut.class);
        AiExchangeOut aiExchangeOut1 = new AiExchangeOut();
        aiExchangeOut1.setId(1L);
        AiExchangeOut aiExchangeOut2 = new AiExchangeOut();
        aiExchangeOut2.setId(aiExchangeOut1.getId());
        assertThat(aiExchangeOut1).isEqualTo(aiExchangeOut2);
        aiExchangeOut2.setId(2L);
        assertThat(aiExchangeOut1).isNotEqualTo(aiExchangeOut2);
        aiExchangeOut1.setId(null);
        assertThat(aiExchangeOut1).isNotEqualTo(aiExchangeOut2);
    }
}
