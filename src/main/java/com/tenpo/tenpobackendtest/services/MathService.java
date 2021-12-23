package com.tenpo.tenpobackendtest.services;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    public double sum(double value1, double value2) {
        return value1 + value2;
    }
}
