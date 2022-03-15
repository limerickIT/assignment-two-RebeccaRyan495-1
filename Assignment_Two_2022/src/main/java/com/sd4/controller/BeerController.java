/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.service.BeerService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
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
public class BeerController {
    
    @Autowired
    private BeerService beerService;
    
    @RequestMapping("beers")
    
    @GetMapping(value="beers", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Beer>> getAll() {
        List<Beer> aList = beerService.findAll();
        
        if(aList.isEmpty())
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else
        {
            Link selfLink = linkTo(methodOn(BeerController.class).getAll()).withSelfRel();
            return ResponseEntity.ok(aList);
        }
    }
    @GetMapping(value="beersHAOS", produces=MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<Beer> getAllBeerHAOS(){
        List<Beer> aList = beerService.findAll();
        
        for(final Beer b : aList)
        {
            //this will eventually be link to beer details drilldown, shows ID now
            long id = b.getId();
            Link selfLink = linkTo(BeerController.class).slash(id).withSelfRel();
            b.add(selfLink);
        }
        //nothing currently appearing, trying to show all beers, self
        Link link = linkTo(methodOn(BeerController.class).getAll()).withSelfRel();
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
               Link allLink = linkTo(methodOn(BeerController.class).getAll()).withRel("beers");
               b.get().add(allLink);
               
               return ResponseEntity.ok(b.get()); 
            }
    }
        
    @PostMapping(value="/beers/", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity add(@RequestBody Beer b)
    {
        beerService.saveBeer(b);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @PutMapping(value="/beers/", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@RequestBody Beer b)
    {
        beerService.editBeer(b);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @DeleteMapping(value="/beers/{id}", consumes={MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable long id)
    {
        beerService.deleteBeerByID(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
