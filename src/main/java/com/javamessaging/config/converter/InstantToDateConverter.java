package com.javamessaging.config.converter;

import java.util.Date;
import java.time.Instant;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class InstantToDateConverter implements Converter<Instant, Date>{
    
    @Override
    public Date convert(@NonNull Instant source) {
        return Date.from(source);
    }
}
