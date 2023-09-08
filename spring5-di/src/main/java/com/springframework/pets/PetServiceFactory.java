package com.springframework.pets;

// Ejemplo de uso del patrón Factory con Spring Framework

// Se creará un bean de Spring de esta clase a través de la clase de configuración "GreetingServiceConfig"
public class PetServiceFactory {

	public PetService getPetService(String petType) {
		switch(petType) {
			case "dog":
				return new DogPetService();
			case "cat":
				return new CatPetService();
			default:
				return new DogPetService();
		}
	}
}
