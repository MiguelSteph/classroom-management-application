package com.classmanagement.resourceserver.dtos;

import lombok.Data;

@Data
public class BookingRequestSummaryDto {
    private int pendingCount;
    private int rejectedCount;
    private int cancelledCount;
    private int approvedCount;
    private int totalCount;
}
