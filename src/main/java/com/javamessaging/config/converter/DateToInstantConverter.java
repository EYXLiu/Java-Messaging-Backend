package com.javamessaging.config.converter;

import java.util.Date;
import java.time.Instant;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class DateToInstantConverter implements Converter<Date, Instant>{
    
    @Override
    public Instant convert(@NonNull Date source) {
        return source.toInstant();
    }
    
}
