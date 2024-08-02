package com.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.Entity.Book;
import com.restapi.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookservice;
//------------- Get Books -----------------------
//	@GetMapping("/books")
//	public List<Book> getBooks() 
//	{
//		return this.bookservice.getAllBooks();
//	}

	// Whenever we comment list.add book in BookService at that time in postman
	// we got [] and 200 ok HttpStatus to solv this we use ResponseEntity
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> allBooks = this.bookservice.getAllBooks();
		if (allBooks.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(allBooks);
	}

//	@GetMapping("/books/{id}")
//	public Book getBook(@PathVariable int id) 
//	{
//		return bookservice.getBookById(id);
//	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable int id) {
		Book book = bookservice.getBookById(id);
		if (book == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(book));
	}

//------------Post Book ---------------------
//	@PostMapping("/books")
//	public Book addBook(@RequestBody Book book) {
//		Book b = this.bookservice.addBook(book);
//		return b;
//	}

	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		try {
			Book b = this.bookservice.addBook(book);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	// ------------ Delete Book-----------------
//	@DeleteMapping("/books/{id}")
//	public void deleteBookById(@PathVariable int id) 
//	{
//		 this.bookservice.deleteBookById(id);
//		
//	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBookById(@PathVariable int id) {
		try {

			this.bookservice.deleteBookById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	// ----------Update Book Data------------------
//	@PutMapping("/books/{id}")
//	//we used (Book book ......)here bcz whenever we update data at that 
//	//time new object will come
//	public Book updateBookById(@RequestBody Book book,@PathVariable int id) 
//	{
//		 this.bookservice.updateBookById(book,id);	
//		 return book;
//	}

	@PutMapping("/books/{id}")
	// we used (Book book ......)here bcz whenever we update data at that
	// time new object will come
	public ResponseEntity<Book> updateBookById(@RequestBody Book book, @PathVariable int id) {
		try {
			this.bookservice.updateBookById(book, id);
			return ResponseEntity.status(HttpStatus.OK).body(book);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
