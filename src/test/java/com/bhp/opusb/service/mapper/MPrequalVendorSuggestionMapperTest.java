package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPrequalVendorSuggestionMapperTest {

    private MPrequalVendorSuggestionMapper mPrequalVendorSuggestionMapper;

    @BeforeEach
    public void setUp() {
        mPrequalVendorSuggestionMapper = new MPrequalVendorSuggestionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPrequalVendorSuggestionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPrequalVendorSuggestionMapper.fromId(null)).isNull();
    }
}
