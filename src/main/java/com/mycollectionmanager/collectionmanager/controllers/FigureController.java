package com.mycollectionmanager.collectionmanager.controllers;


import com.mycollectionmanager.collectionmanager.services.FigureService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/figures")
public class FigureController {

    private final FigureService figureService;


    public FigureController(FigureService figureService) {
        this.figureService = figureService;
    }
}
