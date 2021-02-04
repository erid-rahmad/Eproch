package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdWatchListDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdWatchListDTO.class);
        AdWatchListDTO adWatchListDTO1 = new AdWatchListDTO();
        adWatchListDTO1.setId(1L);
        AdWatchListDTO adWatchListDTO2 = new AdWatchListDTO();
        assertThat(adWatchListDTO1).isNotEqualTo(adWatchListDTO2);
        adWatchListDTO2.setId(adWatchListDTO1.getId());
        assertThat(adWatchListDTO1).isEqualTo(adWatchListDTO2);
        adWatchListDTO2.setId(2L);
        assertThat(adWatchListDTO1).isNotEqualTo(adWatchListDTO2);
        adWatchListDTO1.setId(null);
        assertThat(adWatchListDTO1).isNotEqualTo(adWatchListDTO2);
    }
}
