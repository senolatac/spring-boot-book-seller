package com.sha.springbootbookseller.repository;

import com.sha.springbootbookseller.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sa
 * @date 3.07.2021
 * @time 17:55
 */
public interface IBookRepository extends JpaRepository<Book, Long>
{
}
