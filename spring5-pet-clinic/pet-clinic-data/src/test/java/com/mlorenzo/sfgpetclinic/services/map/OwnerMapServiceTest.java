package com.mlorenzo.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mlorenzo.sfgpetclinic.model.Owner;

class OwnerMapServiceTest {
	final static Long OWNER_ID = 1L;
	final static String LASTNAME = "Smith";
	
	OwnerMapService ownerMapService;
	
	@BeforeEach
	void setUp(){
		ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
		ownerMapService.save(Owner.builder().id(OWNER_ID).lastName(LASTNAME).build());
	}
	
	@Test
	void findAllTest() {
		Set<Owner> owners = ownerMapService.findAll();
		assertEquals(1,owners.size());
	}
	
	@Test
	void findByIdTest() {
		Owner owner = ownerMapService.findById(OWNER_ID);
		assertEquals(OWNER_ID,owner.getId());
	}
	
	@Test
	void saveExistingIdTest() {
		Long id = 2L;
		Owner owner2 = Owner.builder().id(id).build(); 
		Owner savedOwner = ownerMapService.save(owner2);
		assertEquals(id, savedOwner.getId());
	}
	
	@Test
	void saveNoIdTest() {
		Owner savedOwner = ownerMapService.save(Owner.builder().build());
		assertNotNull(savedOwner);
		assertNotNull(savedOwner.getId());
	}
	
	@Test
	void deleteTest() {
		ownerMapService.delete(ownerMapService.findById(OWNER_ID));
		assertEquals(0,ownerMapService.findAll().size());
	}
	
	@Test
	void deleteByIdTest() {
		ownerMapService.deleteById(OWNER_ID);
		assertEquals(0,ownerMapService.findAll().size());
	}
	
	@Test
	void findByLastNameTest() {
		Owner smith = ownerMapService.findByLastName(LASTNAME);
		assertNotNull(smith);
		assertEquals(OWNER_ID,smith.getId());
	}
	
	@Test
	void findByLastNameNotFoundTest() {
		Owner smith = ownerMapService.findByLastName("foo");
		assertNull(smith);
	}

}
