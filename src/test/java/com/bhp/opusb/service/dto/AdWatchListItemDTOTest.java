package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdWatchListItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdWatchListItemDTO.class);
        AdWatchListItemDTO adWatchListItemDTO1 = new AdWatchListItemDTO();
        adWatchListItemDTO1.setId(1L);
        AdWatchListItemDTO adWatchListItemDTO2 = new AdWatchListItemDTO();
        assertThat(adWatchListItemDTO1).isNotEqualTo(adWatchListItemDTO2);
        adWatchListItemDTO2.setId(adWatchListItemDTO1.getId());
        assertThat(adWatchListItemDTO1).isEqualTo(adWatchListItemDTO2);
        adWatchListItemDTO2.setId(2L);
        assertThat(adWatchListItemDTO1).isNotEqualTo(adWatchListItemDTO2);
        adWatchListItemDTO1.setId(null);
        assertThat(adWatchListItemDTO1).isNotEqualTo(adWatchListItemDTO2);
    }
}
