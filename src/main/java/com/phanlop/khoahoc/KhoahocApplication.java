package com.phanlop.khoahoc;

import com.phanlop.khoahoc.DAO.CourseRepository;
import com.phanlop.khoahoc.DAO.UserRepository;
import com.phanlop.khoahoc.Entity.Course;
import com.phanlop.khoahoc.Entity.User;
import com.phanlop.khoahoc.Entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing // Cái này để lưu ngày createDate với modifiedDate - đừng quan tâm
@AllArgsConstructor
public class KhoahocApplication implements CommandLineRunner {
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;


//	public KhoahocApplication(@Autowired CourseRepository courseRepository, @Autowired UserRepository userRepository){
//		this.userRepository = userRepository;
//		this.courseRepository = courseRepository;
//	}

	public static void main(String[] args) {
		SpringApplication.run(KhoahocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("duy123");
		user.setPassword("12345");
		user.setEmail("duy@gmail.com");
		this.userRepository.saveAndFlush(user);

		Course course = new Course();
		course.setCourseName("Khoa hoc Java Spring");
		course.setCourseDes("Hoc Spring Boot truc tuyen nhanh chong");
		course.setCourseOwner(user);
		courseRepository.saveAndFlush(course);

		courseRepository.findAll().forEach(item ->{
			System.out.println(item.getCourseId());
			System.out.println(item.getCourseName());
			System.out.println(item.getCourseOwner());
		});
	}
}
