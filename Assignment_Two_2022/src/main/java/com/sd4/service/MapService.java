/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.model.Map;
import com.sd4.repository.BeerRepository;
import com.sd4.repository.BreweryRepository;
import com.sd4.repository.MapRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MapService {

    @Autowired
    private MapRepository mapRepo;

    public Optional<Map> findOne(Long id) {
        return mapRepo.findById(id);
    }

    public List<Map> findAll() {
        return (List<Map>) mapRepo.findAll();
    }

    public long count() {
        return mapRepo.count();
    }
  
    
    public void editMap(Map m)  {
        mapRepo.save(m);
    }  
    
    public void saveMap(Map m) {
        mapRepo.save(m);
    } 
    
    public void deleteMapByID(long mapID) {
        mapRepo.deleteById(mapID);
    }
    
}//end class
