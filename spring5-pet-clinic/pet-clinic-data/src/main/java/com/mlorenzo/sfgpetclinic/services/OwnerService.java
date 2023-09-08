package com.mlorenzo.sfgpetclinic.services;

import java.util.List;

import com.mlorenzo.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner,Long>{
	Owner findByLastName(String lastName);
	List<Owner> findAllByLastNameLike(String lastName);
}
