/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.repository.BeerRepository;
import com.sd4.repository.BreweryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BreweryService {

    @Autowired
    private BreweryRepository breweryRepo;

    public Optional<Brewery> findOne(Long id) {
        return breweryRepo.findById(id);
    }

    public List<Brewery> findAll() {
        return (List<Brewery>) breweryRepo.findAll();
    }

    public long count() {
        return breweryRepo.count();
    }
  
    
    public void editBeer(Brewery b) {
        breweryRepo.save(b);
    }  
    
    public void saveBeer(Brewery b) {
        breweryRepo.save(b);
    } 
    
    public void deleteBeerByID(long beerID) {
        breweryRepo.deleteById(beerID);
    }
    
}//end class
