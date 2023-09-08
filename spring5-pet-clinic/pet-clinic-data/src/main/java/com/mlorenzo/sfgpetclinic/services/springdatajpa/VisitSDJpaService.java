package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.repositories.VisitRepository;
import com.mlorenzo.sfgpetclinic.services.VisitService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Profile("springdatajpa") // Debido a que tenemos 2 implementaciones de la interfaz "VisitService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "springdatajpa"
public class VisitSDJpaService implements VisitService {
	private final VisitRepository visitRepository;

	@Override
	public Set<Visit> findAll() {
		Set<Visit> setVisits = new HashSet<Visit>();
		visitRepository.findAll().forEach(setVisits::add); // Versión simplifivada de la expresión "visit -> setVisits.add(visit)"
		return setVisits;
	}

	@Override
	public Visit findById(Long id) {
		return visitRepository.findById(id).orElse(null);
	}

	@Override
	public Visit save(Visit object) {
		return visitRepository.save(object);
	}

	@Override
	public void delete(Visit object) {
		visitRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		visitRepository.deleteById(id);
	}

}
