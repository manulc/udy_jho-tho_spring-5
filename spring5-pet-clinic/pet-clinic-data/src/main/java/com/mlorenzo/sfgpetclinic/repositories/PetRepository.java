package com.mlorenzo.sfgpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.sfgpetclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet,Long>{

}
