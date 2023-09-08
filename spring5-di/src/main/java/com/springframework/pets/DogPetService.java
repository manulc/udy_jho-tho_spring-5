package com.springframework.pets;


// Se comentan estas anotaciones porque ahora creamos un bean de Spring de esta clase usando una clase de configuración llamada "GreetingServiceConfig"
//@Profile({"dog", "default"}) // Con esta anotación, se creará el bean de Spring para este servicio cuando el perfil activo sea "dog" o "default"(perfil por defecto cuando no hay ninguno activo)
//@Service
public class DogPetService implements PetService {
	
	@Override
	public String getPetType(){
        return "Dogs are the best!";
    }

}
