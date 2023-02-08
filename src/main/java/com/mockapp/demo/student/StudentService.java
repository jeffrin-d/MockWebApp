package com.mockapp.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//can be used similarly to Component, but indicates
//that this class is the SERVICE class
@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentOptional =
				studentRepository.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email already taken/used");
		}
		studentRepository.save(student);
	}

	public void deleteStudent(Long studentID) {
		boolean exists = studentRepository.existsById(studentID);
		if (!exists) {
			throw new IllegalStateException(
					"student with id:" + studentID + " does not exist (DNE)"
			);
		}
		studentRepository.deleteById(studentID);
	}

	@Transactional
	public void updateStudent(
			Long studentID, String name, String email) {
		Student student = studentRepository.findById(studentID)
				.orElseThrow(() -> new IllegalStateException(
						"student with id: " + studentID + " does not exist"
				));
		if (name != null && name.length() > 0 &&
				!Objects.equals(student.getName(), name)) {
			student.setName(name);
		}
		if (email != null && email.length() > 0 &&
				!Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional =
					studentRepository.findStudentByEmail(email);
			if (studentOptional.isPresent()) {
				throw new IllegalStateException("email already taken");
			}
			student.setEmail(email);
		}
	}
}
