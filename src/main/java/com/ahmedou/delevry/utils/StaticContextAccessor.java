package com.ahmedou.delevry.utils;

import com.ahmedou.delevry.repository.ParametresRepository;
import org.springframework.stereotype.Component;

@Component
public class StaticContextAccessor {
    public static ParametresRepository parametresRepository;
    
    public StaticContextAccessor(ParametresRepository repository) {
        StaticContextAccessor.parametresRepository = repository;
    }
}

