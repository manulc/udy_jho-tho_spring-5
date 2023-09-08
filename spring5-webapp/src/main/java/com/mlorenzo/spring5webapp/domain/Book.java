package com.mlorenzo.spring5webapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // Opcional porque es el valor por defecto del atributo "strategy"
	private Long id;
	
	private String title;
	private String isbn;
	
	@ManyToMany
	@JoinTable(name = "authors_books",
		joinColumns = @JoinColumn(name = "book_id"), // Opcional, ya que por defecto utiliza el nombre "book_id"(nombEntidad_nomPropId)
		inverseJoinColumns = @JoinColumn(name = "author_id")) // Opcional, ya que por defecto utiliza el nombre "author_id"(nombEntidadInv_nomPropId)
	private Set<Author> authors;
	
	@ManyToOne
	@JoinColumn(name = "publisher_id") // Opcional, ya que por defecto utiliza el nombre "publisher_id"(nombEntidad_nomPropId) para la clave foránea que viajará a la tabla de esta clase entidad "Book"
	private Publisher publisher;
	
	public Book() {
		this.authors = new HashSet<>();
	}

	public Book(String title, String isbn) {
		this();
		this.title = title;
		this.isbn = isbn;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
}
