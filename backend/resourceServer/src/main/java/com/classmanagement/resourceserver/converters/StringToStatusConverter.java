package com.classmanagement.resourceserver.converters;

import com.classmanagement.resourceserver.entities.Status;
import org.springframework.core.convert.converter.Converter;

public class StringToStatusConverter implements Converter<String, Status> {

    @Override
    public Status convert(String s) {
        s = s.toLowerCase();
        s = s.substring(0, 1).toUpperCase() + s.substring(1);
        return Status.valueOf(s);
    }
}
