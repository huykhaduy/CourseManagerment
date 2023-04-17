package com.phanlop.khoahoc;

import com.phanlop.khoahoc.Entity.*;
import com.phanlop.khoahoc.Repository.CourseRepository;
import com.phanlop.khoahoc.Repository.UserCourseRepository;
import com.phanlop.khoahoc.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableJpaAuditing // Cái này để lưu ngày createDate với modifiedDate - đừng quan tâm
@AllArgsConstructor
public class KhoahocApplication implements CommandLineRunner {
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final UserCourseRepository userCourseRepository;
	private final PasswordEncoder passwordEncoder;


//	public KhoahocApplication(@Autowired CourseRepository courseRepository, @Autowired UserRepository userRepository){
//		this.userRepository = userRepository;
//		this.courseRepository = courseRepository;
//	}

	public static void main(String[] args) {
		SpringApplication.run(KhoahocApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		// Tạo user sở hữu course
		User user = new User();
		user.setPassword(passwordEncoder.encode("12345"));
		user.setEmail("duy@gmail.com");
		user.setUserRole(UserRole.ADMIN);
		this.userRepository.saveAndFlush(user);

		// Tạo student trong course
		User student = new User();
		student.setPassword(passwordEncoder.encode("123"));
		student.setEmail("stu@gmail.com");
		student.setUserRole(UserRole.STUDENT);
		this.userRepository.save(student);

		Course course = new Course();
		course.setCourseName("Khoa hoc Java Spring");
		course.setCourseDes("Hoc Spring Boot truc tuyen nhanh chong");
		course.setCourseOwner(user);
		courseRepository.save(course);

		UserCourse userCourse = new UserCourse();
		userCourse.setAccessType(AccessType.PENDING);
		userCourse.setUser(student);
		userCourse.setCourse(course);

		userCourseRepository.saveAndFlush(userCourse);


		// Thêm vào bảng user_course
//		course.getUserCourses().add(student);
//		student.getCourses().add(course);
//		// Chỉ cần lưu 1 bên là được
//		userRepository.saveAndFlush(student);

		courseRepository.findAll().forEach(item ->{
			System.out.println(item.getCourseID());
			System.out.println(item.getCourseName());
			System.out.println(item.getCourseOwner());
		});
	}
}
