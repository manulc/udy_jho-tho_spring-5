package com.mlorenzo.sfgpetclinic.services.map;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.services.VisitService;

@Service
@Profile({"default","map"}) // Debido a que tenemos 2 implementaciones de la interfaz "VisitService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "default" o "map"
public class VisitMapService  extends AbstractMapService<Visit> implements VisitService{
	
	@Override
	public Set<Visit> findAll(){
		return super.findAll();
	}
	
	@Override
	public Visit findById(Long id) {
		return super.findById(id);
	}
	
	@Override
	public Visit save(Visit object) {
		if(object.getPet() == null || object.getPet().getOwner() == null || object.getPet().getId() == null || object.getPet().getOwner().getId() == null)
			throw new RuntimeException("Invalid Visit");
		
		return super.save(object);
	}
	
	@Override
	public void delete(Visit object) {
		super.delete(object);
	}
	
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}

}
