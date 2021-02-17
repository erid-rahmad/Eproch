package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MVendorSuggestionMapperTest {

    private MVendorSuggestionMapper mVendorSuggestionMapper;

    @BeforeEach
    public void setUp() {
        mVendorSuggestionMapper = new MVendorSuggestionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mVendorSuggestionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mVendorSuggestionMapper.fromId(null)).isNull();
    }
}
