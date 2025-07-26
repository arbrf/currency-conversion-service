package com.in28minutes.microservice.currency_conversion_service.controller;

import com.in28minutes.microservice.currency_conversion_service.CurrencyConversion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {
    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyCovnersion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
     return  new CurrencyConversion(10001L,from,to,quantity,BigDecimal.ONE,BigDecimal.ONE,"");

    }
}
