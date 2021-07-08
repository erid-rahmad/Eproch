package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidding} entity.
 */
public class DashboardSpendByCostCenter extends AbstractAuditingDTO {

    private String name;
    private String type;
    private List<BigDecimal> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BigDecimal> getData() {
        return data;
    }

    public void setData(List<BigDecimal> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DashboardSpendByCostCenter{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", data=" + data +
            '}';
    }
}
