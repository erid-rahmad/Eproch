package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AiExchangeInDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AiExchangeInDTO.class);
        AiExchangeInDTO aiExchangeInDTO1 = new AiExchangeInDTO();
        aiExchangeInDTO1.setId(1L);
        AiExchangeInDTO aiExchangeInDTO2 = new AiExchangeInDTO();
        assertThat(aiExchangeInDTO1).isNotEqualTo(aiExchangeInDTO2);
        aiExchangeInDTO2.setId(aiExchangeInDTO1.getId());
        assertThat(aiExchangeInDTO1).isEqualTo(aiExchangeInDTO2);
        aiExchangeInDTO2.setId(2L);
        assertThat(aiExchangeInDTO1).isNotEqualTo(aiExchangeInDTO2);
        aiExchangeInDTO1.setId(null);
        assertThat(aiExchangeInDTO1).isNotEqualTo(aiExchangeInDTO2);
    }
}
