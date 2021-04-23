package com.classmanagement.resourceserver.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequestsPageDto {
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int currentPage;
    private int pageSize;
    private List<BookingRequestDto> data;
}
