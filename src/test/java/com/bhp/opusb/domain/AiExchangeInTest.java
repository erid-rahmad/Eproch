package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AiExchangeInTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AiExchangeIn.class);
        AiExchangeIn aiExchangeIn1 = new AiExchangeIn();
        aiExchangeIn1.setId(1L);
        AiExchangeIn aiExchangeIn2 = new AiExchangeIn();
        aiExchangeIn2.setId(aiExchangeIn1.getId());
        assertThat(aiExchangeIn1).isEqualTo(aiExchangeIn2);
        aiExchangeIn2.setId(2L);
        assertThat(aiExchangeIn1).isNotEqualTo(aiExchangeIn2);
        aiExchangeIn1.setId(null);
        assertThat(aiExchangeIn1).isNotEqualTo(aiExchangeIn2);
    }
}
