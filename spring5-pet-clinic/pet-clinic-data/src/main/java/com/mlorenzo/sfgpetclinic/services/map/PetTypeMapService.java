package com.mlorenzo.sfgpetclinic.services.map;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.PetType;
import com.mlorenzo.sfgpetclinic.services.PetTypeService;

@Service
@Profile({"default","map"}) // Debido a que tenemos 2 implementaciones de la interfaz "PetTypeService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "default" o "map"
public class PetTypeMapService extends AbstractMapService<PetType> implements PetTypeService {

	@Override
	public Set<PetType> findAll(){
		return super.findAll();
	}
	
	@Override
	public PetType findById(Long id) {
		return super.findById(id);
	}
	
	@Override
	public PetType save(PetType object) {
		return super.save(object);
	}
	
	@Override
	public void delete(PetType object) {
		super.delete(object);
	}
	
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}
}
