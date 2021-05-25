package com.classmanagement.resourceserver.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SiteDto {
    private Integer id;
    private String code;
    private String name;
    private boolean isEnabled;
    private List<BuildingDto> buildings = new ArrayList<>();
}
