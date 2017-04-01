package com.hendisantika.course.controller;

import java.util.List;

import com.hendisantika.course.model.Course;
import com.hendisantika.course.model.Student;
import com.hendisantika.course.repository.CourseRepository;
import com.hendisantika.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    	repository.delete(studentId);
        return "redirect:/students";
    }    
    
    @RequestMapping(value = "addStudentCourse/{id}", method = RequestMethod.GET)
    public String addCourse(@PathVariable("id") Long studentId, Model model){
    	model.addAttribute("courses", crepository.findAll());
		model.addAttribute("student", repository.findOne(studentId));
    	return "addStudentCourse";
    }
    
    
    @RequestMapping(value="/student/{id}/courses", method=RequestMethod.GET)
	public String studentsAddCourse(@PathVariable Long id, @RequestParam Long courseId, Model model) {
		Course course = crepository.findOne(courseId);
		Student student = repository.findOne(id);

		if (student != null) {
			if (!student.hasCourse(course)) {
				student.getCourses().add(course);
			}
			repository.save(student);
			model.addAttribute("student", crepository.findOne(id));
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
