package com.mlorenzo.spring5webapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mlorenzo.spring5webapp.domain.Author;
import com.mlorenzo.spring5webapp.domain.Book;
import com.mlorenzo.spring5webapp.domain.Publisher;
import com.mlorenzo.spring5webapp.repositories.AuthorRepository;
import com.mlorenzo.spring5webapp.repositories.BookRepository;
import com.mlorenzo.spring5webapp.repositories.PublisherRepository;

@Component
public class BootstapData implements CommandLineRunner {
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;

	public BootstapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.publisherRepository = publisherRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Started in Bootstrap");
		Publisher publisher = new Publisher("SFG Publishing", "Address line 1", "St Petersburg", "FL", "Zip");
		publisherRepository.save(publisher);
		Author eric = new Author("Eric", "Evans");
		Book ddd = new Book("Domain Driven Design", "123123");
		eric.getBooks().add(ddd);
		ddd.getAuthors().add(eric);
		ddd.setPublisher(publisher);
		publisher.getBooks().add(ddd);
		authorRepository.save(eric);
		bookRepository.save(ddd);
		Author rod = new Author("Rod", "Johnson");
		Book noEJB = new Book("J2EE Development without EJB", "3939459459");
		rod.getBooks().add(noEJB);
		noEJB.getAuthors().add(rod);
		noEJB.setPublisher(publisher);
		publisher.getBooks().add(noEJB);
		authorRepository.save(rod);
		bookRepository.save(noEJB);
		System.out.println("Number of books: " + bookRepository.count());
		System.out.println("Number of auhtors: " + authorRepository.count());
		System.out.println("Number of publishers: " + publisherRepository.count());
	}
}
