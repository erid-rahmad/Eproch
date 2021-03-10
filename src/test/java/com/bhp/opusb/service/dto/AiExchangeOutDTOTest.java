package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AiExchangeOutDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AiExchangeOutDTO.class);
        AiExchangeOutDTO aiExchangeOutDTO1 = new AiExchangeOutDTO();
        aiExchangeOutDTO1.setId(1L);
        AiExchangeOutDTO aiExchangeOutDTO2 = new AiExchangeOutDTO();
        assertThat(aiExchangeOutDTO1).isNotEqualTo(aiExchangeOutDTO2);
        aiExchangeOutDTO2.setId(aiExchangeOutDTO1.getId());
        assertThat(aiExchangeOutDTO1).isEqualTo(aiExchangeOutDTO2);
        aiExchangeOutDTO2.setId(2L);
        assertThat(aiExchangeOutDTO1).isNotEqualTo(aiExchangeOutDTO2);
        aiExchangeOutDTO1.setId(null);
        assertThat(aiExchangeOutDTO1).isNotEqualTo(aiExchangeOutDTO2);
    }
}
