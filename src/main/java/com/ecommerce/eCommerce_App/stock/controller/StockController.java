package com.ecommerce.eCommerce_App.stock.controller;

import com.ecommerce.eCommerce_App.stock.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {
    private final StockService stockService;




}
