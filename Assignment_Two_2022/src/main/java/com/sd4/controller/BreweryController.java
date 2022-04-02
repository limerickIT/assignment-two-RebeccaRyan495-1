/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Brewery;
import com.sd4.service.BreweryService;
import com.sd4.service.MapService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rebec
 */
@RestController
@RequestMapping("breweries")
public class BreweryController {
    
    @Autowired
    private BreweryService breweryService;
    private MapService mapService;
    
    @GetMapping(value="/HAOS", produces=MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<Brewery> getAllBreweryHAOS(){
        List<Brewery> aList = breweryService.findAll();
        
        for(final Brewery b : aList)
        {
            long id = b.getId();
            Link selfLink = linkTo(BreweryController.class).slash(id).withSelfRel();
            b.add(selfLink);
            
            Link mapLink = linkTo(BreweryController.class).slash(id).slash("maps").withSelfRel();
            b.add(mapLink);
            
        }
        
        Link link = linkTo(methodOn(BreweryController.class).getAllBreweryHAOS()).withSelfRel();
        CollectionModel<Brewery> result = CollectionModel.of(aList, link);
        return result;
    }
    
      @GetMapping(value ="/{id}", produces={MediaTypes.HAL_JSON_VALUE})
        public ResponseEntity<Brewery> getOne(@PathVariable long id){
            Optional<Brewery> b = breweryService.findOne(id);
            if(!b.isPresent())
            {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else
            {    
               //Link selfLink = new Link("http://localhost:8888/beers/");
               Link allLink = linkTo(methodOn(BreweryController.class).getAllBreweryHAOS()).withRel("breweries");
               b.get().add(allLink);
               
               return ResponseEntity.ok(b.get()); 
            }
    }
       
//    @GetMapping(value ="/{id}/maps", produces={MediaTypes.HAL_JSON_VALUE})
//    public ResponseEntity<Brewery> getMap(@PathVariable long id){
//        
//    }
        
    
}
