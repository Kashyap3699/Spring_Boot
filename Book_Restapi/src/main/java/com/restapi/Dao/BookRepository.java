package com.restapi.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restapi.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	//public Book findById(int id);

}
