package com.mlorenzo.spring5mvcrest.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mlorenzo.spring5mvcrest.domain.Category;
import com.mlorenzo.spring5mvcrest.domain.Customer;
import com.mlorenzo.spring5mvcrest.domain.Vendor;
import com.mlorenzo.spring5mvcrest.repositories.CategoryRepository;
import com.mlorenzo.spring5mvcrest.repositories.CustomerRepository;
import com.mlorenzo.spring5mvcrest.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner{
	private final CategoryRepository categoryRepository ;
	private final CustomerRepository customerRepository ;
	private final VendorRepository vendorRepository;

	@Override
	public void run(String... args) throws Exception {
		loadCategories();
		loadCustomers();
		loadVendors();
	}
	
	private void loadCategories() {
		List<Category> categories = new ArrayList<Category>();
		Category fruits = new Category();
        fruits.setName("Fruits");
        categories.add(fruits);
        Category dried = new Category();
        dried.setName("Dried");
        categories.add(dried);
        Category fresh = new Category();
        fresh.setName("Fresh");
        categories.add(fresh);
        Category exotic = new Category();
        exotic.setName("Exotic");
        categories.add(exotic);
        Category nuts = new Category();
        nuts.setName("Nuts");
        categories.add(nuts);
        categoryRepository.saveAll(categories);
        System.out.println("Category Data Loaded = " + categoryRepository.count());
	}
	
	private void loadCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        Customer anne = new Customer();
        anne.setFirstname("Anne");
        anne.setLastname("Hine");
        customers.add(anne);
        Customer alice = new Customer();
        alice.setFirstname("Alice");
        alice.setLastname("Eastman");
        customers.add(alice);
        Customer freddy = new Customer();
        freddy.setFirstname("Freddy");
        freddy.setLastname("Meyers");
        customers.add(freddy);
        Customer martin = new Customer();
        martin.setFirstname("Martin");
        martin.setLastname("Jacob");
        customers.add(martin);
        Customer jhon = new Customer();
        jhon.setFirstname("Jhon");
        jhon.setLastname("Doe");
        customers.add(jhon);
        customerRepository.saveAll(customers);
        System.out.println("Customer Data Loaded = " + customerRepository.count());
		
	}
	
	private void loadVendors() {
		Vendor vendor1 = new Vendor();
		vendor1.setName("Western Tasty Fruits Ltd.");
		Vendor vendor2 = new Vendor();
		vendor2.setName("Exotic Fruits Company");
		Vendor vendor3 = new Vendor();
		vendor3.setName("Home Fruits");
		Vendor vendor4 = new Vendor();
		vendor4.setName("Fun Fresh Fruits Ltd.");
		Vendor vendor5 = new Vendor();
		vendor5.setName("Nuts for Nuts Company");
		vendorRepository.save(vendor1);
		vendorRepository.save(vendor2);
		vendorRepository.save(vendor3);
		vendorRepository.save(vendor4);
		vendorRepository.save(vendor5);
		System.out.println("Vendor Data Loaded = " + vendorRepository.count());
	}

}
