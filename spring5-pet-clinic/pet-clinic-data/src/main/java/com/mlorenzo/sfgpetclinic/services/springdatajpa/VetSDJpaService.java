package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mlorenzo.sfgpetclinic.model.Vet;
import com.mlorenzo.sfgpetclinic.repositories.VetRepository;
import com.mlorenzo.sfgpetclinic.services.VetService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Profile("springdatajpa") // Debido a que tenemos 2 implementaciones de la interfaz "VetService" y solo puede haber una en cada arranque de la aplicación, con esta anotación hacemos que se utiliza la implementación de esta clase cuando se active el perfil "springdatajpa"
public class VetSDJpaService implements VetService {
	private final VetRepository vetRepository;

	@Override
	public Set<Vet> findAll() {
		Set<Vet> setVets = new HashSet<Vet>();
		vetRepository.findAll().forEach(setVets::add); // Versión simplifivada de la expresión "vet -> setVets.add(vet)"
		return setVets;
	}

	@Override
	public Vet findById(Long id) {
		return vetRepository.findById(id).orElse(null);
	}

	@Override
	public Vet save(Vet object) {
		return vetRepository.save(object);
	}

	@Override
	public void delete(Vet object) {
		vetRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		vetRepository.deleteById(id);
	}
}
