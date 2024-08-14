package com.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.Dao.BookRepository;
import com.restapi.Entity.Book;

//@Componant
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

//	private static List<Book> list = new ArrayList<>();
//
//	static {
//		list.add(new Book(2, "Python Book", "Dhaval"));
//		list.add(new Book(3, "Javascript Book", "Krunal"));
//		list.add(new Book(4, "React Book", "Kunj"));
//	}

	// ---------------- get all books -----------------

	public List<Book> getAllBooks() {

		List<Book> list = (List<Book>) bookRepository.findAll();
		return list;
	}

	// --------------Get single book by id---------------
	public Book getBookById(int id) {
		Optional<Book> book = null;
		try {
			// book = list.stream().filter(e -> e.getId() == id).findFirst().get();
			book = bookRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book.get();
	}

	// --------------- Adding Book ---------------------
	public Book addBook(Book b) {
		Book save = bookRepository.save(b);

		return save;

	}

	// ---------------Delete Book-------------------

	public void deleteBookById(int id) {
		// list = list.stream().filter(e->e.getId()!=id).collect(Collectors.toList());
		bookRepository.deleteById(id);
	}

	// --------------Update Book----------------
	public void updateBookById(Book book, int id) {
//		list = list.stream().map(e->{
//			if(e.getId()==id)
//			{
//				e.setBookName(book.getBookName());
//				e.setAuthor(book.getAuthor());
//								
//			}
//			return e;
//		}).collect(Collectors.toList())	;

		book.setId(id);
		bookRepository.save(book);
	}

}
