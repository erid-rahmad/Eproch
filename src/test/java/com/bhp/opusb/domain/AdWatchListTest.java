package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdWatchListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdWatchList.class);
        AdWatchList adWatchList1 = new AdWatchList();
        adWatchList1.setId(1L);
        AdWatchList adWatchList2 = new AdWatchList();
        adWatchList2.setId(adWatchList1.getId());
        assertThat(adWatchList1).isEqualTo(adWatchList2);
        adWatchList2.setId(2L);
        assertThat(adWatchList1).isNotEqualTo(adWatchList2);
        adWatchList1.setId(null);
        assertThat(adWatchList1).isNotEqualTo(adWatchList2);
    }
}
