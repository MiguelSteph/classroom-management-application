package com.classmanagement.resourceserver.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRequestSummaryDto {
    private int pendingCount;
    private int rejectedCount;
    private int cancelledCount;
    private int approvedCount;
    private int totalCount;
}
