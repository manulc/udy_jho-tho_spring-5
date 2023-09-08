package com.mlorenzo.sfgpetclinic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mlorenzo.sfgpetclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner,Long>{
	Owner findByLastName(String lastName);
	List<Owner> findAllByLastNameLike(String lastName);
}
