package com.springframework.pets;


// Se comentan estas anotaciones porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Profile("cat") // Con esta anotación, se creará el bean de Spring para este servicio cuando el perfil activo sea "cat"
//@Service
public class CatPetService implements PetService {
	
	@Override
    public String getPetType() {
        return "Cats Are the Best!";
    }

}
