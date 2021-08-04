package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractTaskReviewersMapperTest {

    private MContractTaskReviewersMapper mContractTaskReviewersMapper;

    @BeforeEach
    public void setUp() {
        mContractTaskReviewersMapper = new MContractTaskReviewersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractTaskReviewersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractTaskReviewersMapper.fromId(null)).isNull();
    }
}
