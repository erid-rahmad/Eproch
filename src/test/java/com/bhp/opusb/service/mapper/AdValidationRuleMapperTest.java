package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdValidationRuleMapperTest {

    private AdValidationRuleMapper adValidationRuleMapper;

    @BeforeEach
    public void setUp() {
        adValidationRuleMapper = new AdValidationRuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adValidationRuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adValidationRuleMapper.fromId(null)).isNull();
    }
}
