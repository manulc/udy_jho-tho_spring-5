package com.mlorenzo.sfgdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.mlorenzo.sfgdi.config.SfgConfiguration;
import com.mlorenzo.sfgdi.config.SfgConstructorConfig;
import com.mlorenzo.sfgdi.controllers.ConstructorInjectedController;
import com.mlorenzo.sfgdi.controllers.I18nController;
import com.mlorenzo.sfgdi.controllers.MyController;
import com.mlorenzo.sfgdi.controllers.PetController;
import com.mlorenzo.sfgdi.controllers.PropertyInjectedController;
import com.mlorenzo.sfgdi.controllers.SetterInjectedController;
import com.mlorenzo.sfgdi.datasource.FakeDataSource;

// Anotación para que busque anotaciones de Spring(@Component, @Service, @Repository, etc...) dentro de estos paquetes y sus subpaquetes
// Se comenta porque ahora los beans de Spring del paquete "com.springframework.pets" se crean en la clase de configuración "GreetingServiceConfig" del paquete "com.mlorenzo.sfgdi"
// y, por lo tanto, ya no hace falta buscare anotaciones de Spring en el paquete "com.springframework.pets"
//@ComponentScan(basePackages = {"com.mlorenzo.sfgdi", "com.springframework.pets"})
@SpringBootApplication
public class SfgDiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SfgDiApplication.class, args);
		System.out.println("------- Primary Bean");
		// Obtención de un bean de Spring mediante su nombre
		// Por defecto, Spring utiliza el nombre de la clase(empezando en mínuscula) para el bean que crea de esa clase
		//MyController myController = (MyController)ctx.getBean("myController");
		// Otra forma de obtener el bean de Spring mediante su tipo
		MyController myController = ctx.getBean(MyController.class);
		System.out.println(myController.getGreeting());
		System.out.println("------- Property");
		// Obtención de un bean de Spring mediante su nombre
		PropertyInjectedController propertyInjectedController = (PropertyInjectedController)ctx.getBean("propertyInjectedController");
		System.out.println(propertyInjectedController.getGreeting());
		System.out.println("------- Setter");
		// Obtención de un bean de Spring mediante su tipo
		SetterInjectedController setterInjectedController = ctx.getBean(SetterInjectedController.class);
		System.out.println(setterInjectedController.getGreeting());
		System.out.println("------- Constructor");
		// Obtención de un bean de Spring mediante su nombre
		ConstructorInjectedController constructorInjectedController = (ConstructorInjectedController)ctx.getBean("constructorInjectedController");
		System.out.println(constructorInjectedController.getGreeting());
		System.out.println("------- I18n");
		// Obtención de un bean de Spring mediante su tipo
		I18nController i18nController = ctx.getBean(I18nController.class);
		System.out.println(i18nController.getGreeting());
		System.out.println("--- The Best Pet is ---");
		// Obtención de un bean de Spring mediante su nombre(Indicamos también su tipo para evitar hacer un casting)
		PetController petController = ctx.getBean("petController", PetController.class);
		System.out.println(petController.whichPetIsTheBest());
		System.out.println("--- Bean Scopes ---");
		// Obtención de un bean de Spring mediante su tipo
		SingletonBean singletonBean1 = ctx.getBean(SingletonBean.class);
		System.out.println(singletonBean1.getMyScope());
		// Obtención de un bean de Spring mediante su tipo
		SingletonBean singletonBean2 = ctx.getBean(SingletonBean.class);
		System.out.println(singletonBean2.getMyScope());
		// Obtención de un bean de Spring mediante su nombre(Indicamos también su tipo para evitar hacer un casting)
		PrototypeBean prototypeBean1 = ctx.getBean("prototypeBean", PrototypeBean.class);
		System.out.println(prototypeBean1.getMyScope());
		// Obtención de un bean de Spring mediante su nombre(Indicamos también su tipo para evitar hacer un casting)
		PrototypeBean prototypeBean2 = ctx.getBean("prototypeBean", PrototypeBean.class);
		System.out.println(prototypeBean2.getMyScope());
		System.out.println("------- Fake Data Source");
		// Obtención de un bean de Spring mediante su nombre
		FakeDataSource fakeDataSource = (FakeDataSource)ctx.getBean("fakeDataSource");
		System.out.println(fakeDataSource.getUsername());
		System.out.println(fakeDataSource.getPassword());
		System.out.println(fakeDataSource.getJdbcUrl());
		System.out.println("------- Config Props Bean");
		// Obtención de un bean de Spring mediante su nombre(Indicamos también su tipo para evitar hacer un casting)
		SfgConfiguration sfgConfiguration = ctx.getBean("sfgConfiguration", SfgConfiguration.class);
		System.out.println(sfgConfiguration.getUsername());
		System.out.println(sfgConfiguration.getPassword());
		System.out.println(sfgConfiguration.getJdbcUrl());
		System.out.println("------- Constructor Binding");
		// Obtención de un bean de Spring mediante su tipo
		SfgConstructorConfig sfgConstructorConfig = ctx.getBean( SfgConstructorConfig.class);
		System.out.println(sfgConstructorConfig.getUsername());
		System.out.println(sfgConstructorConfig.getPassword());
		System.out.println(sfgConstructorConfig.getJdbcUrl());
	}

}
