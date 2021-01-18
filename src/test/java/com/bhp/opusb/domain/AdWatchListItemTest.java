package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdWatchListItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdWatchListItem.class);
        AdWatchListItem adWatchListItem1 = new AdWatchListItem();
        adWatchListItem1.setId(1L);
        AdWatchListItem adWatchListItem2 = new AdWatchListItem();
        adWatchListItem2.setId(adWatchListItem1.getId());
        assertThat(adWatchListItem1).isEqualTo(adWatchListItem2);
        adWatchListItem2.setId(2L);
        assertThat(adWatchListItem1).isNotEqualTo(adWatchListItem2);
        adWatchListItem1.setId(null);
        assertThat(adWatchListItem1).isNotEqualTo(adWatchListItem2);
    }
}
