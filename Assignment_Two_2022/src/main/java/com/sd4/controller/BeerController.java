/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.service.BeerService;
import com.sd4.service.BreweryService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rebec
 */
@RestController
@RequestMapping("beers")
public class BeerController {
    
    @Autowired
    private BeerService beerService;
    private BreweryService breweryService;
    
    @GetMapping(value="HAOS", produces=MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<Beer> getAllBeerHAOS(){
        List<Beer> aList = beerService.findAll();
        
        for(final Beer b : aList)
        {
            
            long id = b.getId();
            Link selfLink = linkTo(BeerController.class).slash(id).withSelfRel();
            b.add(selfLink);
            
            Link drilldown = linkTo(BeerController.class).slash(id).slash("drilldown").withSelfRel();
            b.add(drilldown);
            
        }
        
        Link link = linkTo(methodOn(BeerController.class).getAllBeerHAOS()).withSelfRel();
        CollectionModel<Beer> result = CollectionModel.of(aList, link);
        return result;
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
               //Link selfLink = new Link("http://localhost:8888/beers/");
               Link allLink = linkTo(methodOn(BeerController.class).getAllBeerHAOS()).withRel("beers");
               b.get().add(allLink);
               return ResponseEntity.ok(b.get()); 
            }
    }
        
    @PostMapping(consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity add(@RequestBody Beer b)
    {
        beerService.saveBeer(b);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @PutMapping(consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@RequestBody Beer b)
    {
        beerService.editBeer(b);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @DeleteMapping(value="/{id}", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable long id)
    {
        beerService.deleteBeerByID(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @GetMapping(value="/{name}/{id}/drilldown", produces={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Beer> getBeerDetails(@PathVariable long id)
    {
          
    }
    
}
