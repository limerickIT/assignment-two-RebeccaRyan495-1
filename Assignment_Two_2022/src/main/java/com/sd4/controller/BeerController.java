/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.service.BeerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rebec
 */
@RestController
public class BeerController {
    
    @Autowired
    private BeerService beerService;
    @RequestMapping("")
    @GetMapping(value="beers", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Beer>> getAll() {
        List<Beer> aList = beerService.findAll();
        
        if(aList.isEmpty())
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(aList);
        }
    }
    
    @GetMapping(value ="/{id}", produces={MediaTypes.HAL_JSON_VALUE})
        public ResponseEntity<Beer> getOne(@PathVariable long id){
            Optional<Beer> b = beerService.findOne(id);
            if(!b.isPresent())
            {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else
            {    
               Link selfLink = new Link("http://localhost:8888/beers/" + id).withSelfRel();
               b.get().add(selfLink);
               return ResponseEntity.ok(b.get()); 
            }
    }
        
    @PostMapping(value="", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity add(@RequestBody Beer b)
    {
        beerService.saveBeer(b);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
}
