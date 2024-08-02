package com.restapi.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restapi.Entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	public Book findById(int id);

}
