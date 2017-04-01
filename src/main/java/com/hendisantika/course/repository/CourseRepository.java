package com.hendisantika.course.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.hendisantika.course.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long>  {
    
	List<Course> findByName(String name);
}

