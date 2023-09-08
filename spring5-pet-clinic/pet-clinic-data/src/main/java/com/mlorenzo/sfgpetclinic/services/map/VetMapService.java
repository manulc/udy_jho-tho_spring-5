package com.mlorenzo.sfgpetclinic.services.map;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Speciality;
import com.mlorenzo.sfgpetclinic.model.Vet;
import com.mlorenzo.sfgpetclinic.services.SpecialityService;
import com.mlorenzo.sfgpetclinic.services.VetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Profile({"default","map"}) // Debido a que tenemos 2 implementaciones de la interfaz "VetService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "default" o "map"
public class VetMapService extends AbstractMapService<Vet> implements VetService{
	private final SpecialityService specialityService;
	
	@Override
	public Set<Vet> findAll(){
		return super.findAll();
	}
	
	@Override
	public Vet findById(Long id) {
		return super.findById(id);
	}
	
	@Override
	public Vet save(Vet object) {
		if(object.getSpecialities().size() > 0) {
			object.getSpecialities().forEach(speciality -> {
				if(speciality.getId() == null) {
					Speciality savedSpeciality = specialityService.save(speciality);
					speciality.setId(savedSpeciality.getId());
				}
			});
		}
		return super.save(object);
	}
	
	@Override
	public void delete(Vet object) {
		super.delete(object);
	}
	
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}
}
