package com.hendisantika.course.repository;

import java.util.List;

import com.hendisantika.course.model.Student;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findByLastName(String lastname);
    
}
