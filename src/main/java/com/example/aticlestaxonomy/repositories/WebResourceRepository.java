package com.example.aticlestaxonomy.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.aticlestaxonomy.entities.WebResource;

@Repository
public interface WebResourceRepository extends CrudRepository<WebResource, Long> {

}
