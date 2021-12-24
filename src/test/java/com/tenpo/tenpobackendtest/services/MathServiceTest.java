package com.tenpo.tenpobackendtest.services;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathServiceTest {

    private final MathService mathService = new MathService();

    @Test
    public void testSum() {
        double value1 = 300.0;
        double value2 = 400.0;
        double result = mathService.sum(value1, value2);
        assertEquals(700.0, result);
    }

}