package com.bhp.opusb.service.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidding} entity.
 */
public class DashboardChartDTO extends AbstractAuditingDTO {

    private String legendLabel;
    private String xAxisLabel;
    private BigDecimal dataValue;

    public String getLegendLabel() {
        return legendLabel;
    }

    public void setLegendLabel(String legendLabel) {
        this.legendLabel = legendLabel;
    }

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    public void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    @Override
    public String toString() {
        return "DashboardChartDTO{" +
            "legendLabel='" + legendLabel + '\'' +
            ", xAxisLabel='" + xAxisLabel + '\'' +
            ", dataValue=" + dataValue +
            '}';
    }
}
