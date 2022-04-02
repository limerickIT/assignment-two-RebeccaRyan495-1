/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author rebec
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BeerBrewery {
     private String brewName;
     private String beerName;
     private String description;
}
