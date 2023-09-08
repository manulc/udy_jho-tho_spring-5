package com.mlorenzo.sfgpetclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.model.PetType;
import com.mlorenzo.sfgpetclinic.model.Speciality;
import com.mlorenzo.sfgpetclinic.model.Vet;
import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.services.OwnerService;
import com.mlorenzo.sfgpetclinic.services.PetTypeService;
import com.mlorenzo.sfgpetclinic.services.SpecialityService;
import com.mlorenzo.sfgpetclinic.services.VetService;
import com.mlorenzo.sfgpetclinic.services.VisitService;

@Component
public class DataLoader implements CommandLineRunner{
	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;
	private final SpecialityService specialityService;
	private final VisitService visitService;
	
	public DataLoader(OwnerService ownerService,VetService vetService,PetTypeService petTypeService,SpecialityService specialityService,VisitService visitService){
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
		this.specialityService = specialityService;
		this.visitService = visitService;
	}

	@Override
	public void run(String... args) throws Exception {
		int countPetTypes = petTypeService.findAll().size();
		int countSpecialities = specialityService.findAll().size();
		int countOwners = ownerService.findAll().size();
		int countVets = vetService.findAll().size();
		if(countPetTypes == 0 && countSpecialities == 0 && countOwners == 0 && countVets == 0)
			loadData();
	}

	private void loadData() {
		PetType dog = new PetType();
		dog.setName("Dog");
		PetType savedDogPetType = petTypeService.save(dog);
		PetType cat = new PetType();
		cat.setName("Cat");
		PetType savedCatPetType = petTypeService.save(cat);
		Speciality radiology = new Speciality();
		radiology.setDescription("Radiology");
		Speciality savedRadiology = specialityService.save(radiology);
		Speciality surgery = new Speciality();
		surgery.setDescription("Surgery");
		Speciality savedSurgery= specialityService.save(surgery);
		Speciality dentistry = new Speciality();
		dentistry.setDescription("Dentistry");
		Speciality savedDentistry  = specialityService.save(dentistry);
		Owner owner1 = new Owner();
		owner1.setFirstName("Michael");
		owner1.setLastName("Weston");
		owner1.setAddress("123 Brickerel");
		owner1.setCity("Miami");
		owner1.setTelephone("1231231234");
		Pet mikesPet = new Pet();
		mikesPet.setPetType(savedDogPetType);
		mikesPet.setOwner(owner1);
		mikesPet.setBirthDate(LocalDate.now());
		mikesPet.setName("Rosco");
		owner1.getPets().add(mikesPet);
		ownerService.save(owner1);
		Owner owner2 = new Owner();
		owner2.setFirstName("Fiona");
		owner2.setLastName("Glenanne");
		owner2.setAddress("123 Brickerel");
		owner2.setCity("Miami");
		owner2.setTelephone("1231231234");
		Pet fionasCat = new Pet();
		fionasCat.setPetType(savedCatPetType);
		fionasCat.setOwner(owner2);
		fionasCat.setBirthDate(LocalDate.now());
		fionasCat.setName("Just Cat");
		owner2.getPets().add(fionasCat);
		ownerService.save(owner2);
		Visit catVisit = new Visit();
		catVisit.setPet(fionasCat);
		catVisit.setDate(LocalDate.now());
		catVisit.setDescription("Sneezy Kitty");
		visitService.save(catVisit);
		System.out.println("Loaded Owners ...");
		Vet vet1 = new Vet();
		vet1.setFirstName("Sam");
		vet1.setLastName("Axe");
		vet1.getSpecialities().add(savedRadiology);
		vet1.getSpecialities().add(savedDentistry);
		vetService.save(vet1);
		Vet vet2 = new Vet();
		vet2.setFirstName("Jessie");
		vet2.setLastName("Porter");
		vet2.getSpecialities().add(savedSurgery);
		vetService.save(vet2);
		System.out.println("Loaded Vets ...");
	}

}
