package com.hendisantika.course;

import com.hendisantika.course.model.Course;
import com.hendisantika.course.model.Student;
import com.hendisantika.course.repository.CourseRepository;
import com.hendisantika.course.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CrudbootApplication {

    private static final Logger log = LoggerFactory.getLogger(CrudbootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrudbootApplication.class, args);
    }

    /**
     * Save students to H2 DB for testing
     *
     * @param repository
     * @return
     */
    @Bean
    public CommandLineRunner demo(StudentRepository repository, CourseRepository crepository) {
        return (args) -> {
            // save students
            Student student1 = new Student("Uchiha", "Madara", "IT", "uchiha_madara@konohagakure.com");
            repository.save(new Student("Hendi", "Santika", "IT", "hendisantika@konohagakure.com"));
            repository.save(new Student("Uzumaki", "Naruto", "IT", "uzumaki_naruto@konohagakure.com"));
            repository.save(new Student("Hatake", "Kakashi", "IT", "kakahis_hatake@konohagakure.com"));
            repository.save(new Student("Sakura", "Haruno", "Nursery", "sakura_haruno@konohagakure.com"));
            repository.save(new Student("Sasuke", "Uchiha", "Business", "sasuke_uchiha@konohagakure.com"));

            Course course1 = new Course("Programming Java");
            Course course2 = new Course("Spring Boot basics");
            crepository.save(new Course("Marketing 1"));
            crepository.save(new Course("Marketing 2"));

            crepository.save(course1);
            crepository.save(course2);

            Set<Course> courses = new HashSet<Course>();
            courses.add(course1);
            courses.add(course2);

            student1.setCourses(courses);
            repository.save(student1);
        };
    }
}
