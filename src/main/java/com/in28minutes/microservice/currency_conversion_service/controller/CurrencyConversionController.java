package com.in28minutes.microservice.currency_conversion_service.controller;

import com.in28minutes.microservice.currency_conversion_service.CurrencyConversion;
import com.in28minutes.microservice.currency_conversion_service.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;


@RestController
public class CurrencyConversionController {
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyCovnersion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
        System.out.println("Currecny Conversiom Controller called");
        HashMap<String, String> uriVariables=new HashMap<>();
        uriVariables.put("from",from); uriVariables.put("to",to);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<CurrencyConversion> forEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversion=forEntity.getBody();
        return  new CurrencyConversion(currencyConversion.getId(),from,to,quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironnment()+" Rest Template");

    }

    @GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyCovnersionFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){

        CurrencyConversion currencyConversion=currencyExchangeProxy.retrieveExchangeValues(from,to);
        return  new CurrencyConversion(currencyConversion.getId(),from,to,quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironnment()+" Feign");

    }
}
