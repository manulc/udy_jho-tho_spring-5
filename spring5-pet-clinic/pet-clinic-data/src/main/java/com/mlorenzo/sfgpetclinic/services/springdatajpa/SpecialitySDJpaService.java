package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Speciality;
import com.mlorenzo.sfgpetclinic.repositories.SpecialityRepository;
import com.mlorenzo.sfgpetclinic.services.SpecialityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Profile("springdatajpa") // Debido a que tenemos 2 implementaciones de la interfaz "SpecialityService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "springdatajpa"
public class SpecialitySDJpaService implements SpecialityService {
	private final SpecialityRepository specialityRepository;

	@Override
	public Set<Speciality> findAll() {
		Set<Speciality> setSpecialities= new HashSet<Speciality>();
		specialityRepository.findAll().forEach(setSpecialities::add); // Versión simplifivada de la expresión "speciality -> setSpecialities.add(speciality)"
		return setSpecialities;
	}

	@Override
	public Speciality findById(Long id) {
		return specialityRepository.findById(id).orElse(null);
	}

	@Override
	public Speciality save(Speciality object) {
		return specialityRepository.save(object);
	}

	@Override
	public void delete(Speciality object) {
		specialityRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		specialityRepository.deleteById(id);
	}

}
