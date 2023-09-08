package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.repositories.OwnerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class) // Para usar Mockito en esta clase de pruebas
class OwnerSDJpaServiceTest {
	final static String LASTNAME = "Smith";
	final static Long OWNER_ID = 1L;
	
	@Mock // Crea una Mock del repositorio "OwnerRepository"
	OwnerRepository ownerRepository;
	
	@InjectMocks // Esta anotación crea una instancia del servicio "OwnerSDJpaService" e inyecta el Mock del repositorio "ownerRepository"
	OwnerSDJpaService ownerSDJpaService;
	
	Owner returnedOwner;
	
	@BeforeEach
	void setUp() {
		returnedOwner = Owner.builder().id(OWNER_ID).lastName(LASTNAME).build();
	}
	
	@Test
	void findByLastNameTest() {
		when(ownerRepository.findByLastName(anyString())).thenReturn(returnedOwner);		
		Owner smith = ownerSDJpaService.findByLastName(LASTNAME);
		assertEquals(LASTNAME,smith.getLastName());	
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository).findByLastName(anyString());
	}
	
	@Test
	void findAllTest() {
		Set<Owner> returnedOwnerSet = new HashSet<Owner>();
		returnedOwnerSet.add(Owner.builder().id(OWNER_ID).build());
		returnedOwnerSet.add(Owner.builder().id(2L).build());
		when(ownerRepository.findAll()).thenReturn(returnedOwnerSet);
		Set<Owner> owners = ownerSDJpaService.findAll();
		assertNotNull(owners);
		assertEquals(2,owners.size());
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository).findAll();
	}
	
	@Test
	void findByIdTest() {
		Optional<Owner> optionalOwner = Optional.of(returnedOwner);
		when(ownerRepository.findById(any())).thenReturn(optionalOwner);
		Owner owner = ownerSDJpaService.findById(OWNER_ID);
		assertNotNull(owner);
		assertEquals(LASTNAME,owner.getLastName());
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository).findById(any());
	}
	
	@Test
	void findByIdNotFoundTest() {
		Optional<Owner> optionalOwner = Optional.empty();
		when(ownerRepository.findById(any())).thenReturn(optionalOwner);
		Owner owner = ownerSDJpaService.findById(OWNER_ID);
		assertNull(owner);
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository).findById(any());
	}
	
	@Test
	void saveTest() {
		Owner ownerToSave = Owner.builder().id(OWNER_ID).build();
		when(ownerRepository.save(any(Owner.class))).thenReturn(returnedOwner);
		Owner savedOwner = ownerSDJpaService.save(ownerToSave);
		assertNotNull(savedOwner);
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository).save(any(Owner.class));
	}
	
	@Test
	void deleteTest() {
		ownerSDJpaService.delete(returnedOwner);
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository,times(1)).delete(any(Owner.class));
	}
	
	@Test
	void deleteByIdTest() {
		ownerSDJpaService.deleteById(OWNER_ID);
		// Si no se indica el número de llamadas, por defecto es 1
		verify(ownerRepository,times(1)).deleteById(anyLong());
	}

}
