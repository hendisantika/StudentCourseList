package com.hendisantika.course.controller;

import com.hendisantika.course.model.Course;
import com.hendisantika.course.model.Student;
import com.hendisantika.course.repository.CourseRepository;
import com.hendisantika.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
	@Autowired
    private StudentRepository repository;

	@Autowired
    private CourseRepository crepository;
	
	@RequestMapping("/login")
	public String login() {
    	return "login";
    }	
	
	@RequestMapping("/students")
	public String index(Model model) {
		List<Student> students = (List<Student>) repository.findAll();
		model.addAttribute("students", students);
    	return "students";
    }

    @RequestMapping(value = "add")
    public String addStudent(Model model){
    	model.addAttribute("student", new Student());
        return "addStudent";
    }	
	
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Student student){
        repository.save(student);
    	return "redirect:/students";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String editRemoveEmployee(@PathVariable("id") Long studentId, Model model) {
		repository.deleteById(studentId);
		return "redirect:/students";
	}

	@RequestMapping(value = "addStudentCourse/{id}", method = RequestMethod.GET)
	public String addCourse(@PathVariable("id") Long studentId, Model model) {
		model.addAttribute("courses", crepository.findAll());
		model.addAttribute("student", repository.findById(studentId));
		return "addStudentCourse";
	}
    
    
    @RequestMapping(value="/student/{id}/courses", method=RequestMethod.GET)
	public String studentsAddCourse(@PathVariable Long id, @RequestParam Long courseId, Model model) {
		Optional<Course> courseOpt = crepository.findById(courseId);
		Course course = courseOpt.get();
		Optional<Student> studentOpt = repository.findById(id);
		Student student = studentOpt.get();

		if (!studentOpt.isPresent()) {
			if (!student.hasCourse(course)) {
				student.getCourses().add(course);
			}
			repository.save(student);
			model.addAttribute("student", crepository.findById(id));
			model.addAttribute("courses", crepository.findAll());
			return "redirect:/students";
		}

		return "redirect:/students";
	}    
    
    @RequestMapping(value = "getstudents", method = RequestMethod.GET)
    public @ResponseBody List<Student> getStudents() {
            return (List<Student>)repository.findAll();
    }      
}
