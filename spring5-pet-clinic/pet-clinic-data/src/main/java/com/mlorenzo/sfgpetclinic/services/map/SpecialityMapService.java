package com.mlorenzo.sfgpetclinic.services.map;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Speciality;
import com.mlorenzo.sfgpetclinic.services.SpecialityService;

@Service
@Profile({"default","map"}) // Debido a que tenemos 2 implementaciones de la interfaz "SpecialityService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "default" o "map"
public class SpecialityMapService extends AbstractMapService<Speciality> implements SpecialityService{
	
	@Override
	public Set<Speciality> findAll(){
		return super.findAll();
	}
	
	@Override
	public Speciality findById(Long id) {
		return super.findById(id);
	}
	
	@Override
	public Speciality save(Speciality object) {
		return super.save(object);
	}
	
	@Override
	public void delete(Speciality object) {
		super.delete(object);
	}
	
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}
}
