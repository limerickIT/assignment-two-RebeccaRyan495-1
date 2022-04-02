package com.sd4.repository;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.model.Map;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends CrudRepository<Map, Long> {
    
 
}



