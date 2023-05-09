package com.phanlop.khoahoc;

import com.phanlop.khoahoc.Entity.*;
import com.phanlop.khoahoc.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableJpaAuditing // Cái này để lưu ngày createDate với modifiedDate - đừng quan tâm
@AllArgsConstructor
public class KhoahocApplication implements CommandLineRunner{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final DepartmentRepository departmentRepository;
	private final NotifyRepository notifyRepository;
	private final InviteRepository inviteRepository;
	private final ChapterRepository chapterRepository;
	private final RoleRepository roleRepository;
	private EnrollmentRepository enrollmentRepository;

	public static void main(String[] args) {
		SpringApplication.run(KhoahocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeData();
	}

	@Transactional
	public void initializeData(){
		// Tạo các role
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("ROLE_ADMIN");

		Role role2 = new Role();
		role2.setRoleId(2);
		role2.setRoleName("ROLE_STUDENT");

		Role role3 = new Role();
		role3.setRoleId(3);
		role3.setRoleName("ROLE_TEACHER");
		roleRepository.save(role);
		roleRepository.save(role2);
		roleRepository.save(role3);

		// Tạo user
		User adminUser = new User();
		adminUser.setUserId(1L);
		adminUser.setFullName("Duy Huynh");
		adminUser.setEmail("duy@gmail.com");
		adminUser.setAvatar("");
		adminUser.setPassword(passwordEncoder.encode("123456"));
		adminUser.getListRoles().add(role);
		role.getListUsers().add(adminUser);
		userRepository.save(adminUser);
		roleRepository.save(role);

		User guest = new User();
		guest.setUserId(2L);
		guest.setFullName("Guest");
		guest.setEmail("duy2@gmail.com");
		guest.setPassword(passwordEncoder.encode("123456"));
		guest.getListRoles().add(role2);
		role2.getListUsers().add(guest);
		userRepository.save(guest);
		roleRepository.save(role2);

		Department cntt = new Department();
		cntt.setDepartmentId(1);
		cntt.setDepartmentName("Công nghệ thông tin");
		departmentRepository.save(cntt);

		Department laptrinh = new Department();
		laptrinh.setDepartmentId(2);
		laptrinh.setDepartmentName("Lập trình ứng dụng");
		departmentRepository.save(laptrinh);
		// Tạo các course
		for (int i=0;i<20;i++){
			Course course = new Course();
			course.setCourseOwner(adminUser);
			course.setCourseDes("Mô tả khóa học Contrary to popular belief, Lorem Ipsum is not simply random text.");
			course.setCourseName("Khóa học thứ "+i);
			if (i%2==0)
				course.setDepartment(cntt);
			else
				course.setDepartment(laptrinh);
			courseRepository.save(course);

			for (int j=0;j<5;j++){
				Chapter chapter = new Chapter();
				chapter.setChapterTitle(j+".Xin chào thế giới");
				chapter.setChapterSort(j);
				chapter.setChapterVideo("https://www.youtube.com/embed/z2f7RHgvddc");
				chapter.setChapterContent("Nội dung chương Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.");
				chapter.setCourse(course);
				chapterRepository.save(chapter);
			}
			Notify newNotify = new Notify();
			newNotify.setNotiTitle("Xin chào course");
			newNotify.setCourse(course);

			Invite invite1 = new Invite();
			invite1.setInviteEmail("du@gmail.com");
			invite1.setCourse(course);
			notifyRepository.save(newNotify);
			inviteRepository.save(invite1);

//			Enrollment enrollment = new Enrollment();
//			Enrollment.EnrollmentId id = new Enrollment.EnrollmentId();
//			id.setCourseId(course.getCourseID());
//			id.setUserId(guest.getUserId());
//			enrollment.setId(id);
//			enrollment.setUser(guest);
//			enrollment.setCourse(course);
//			enrollmentRepository.save(enrollment);
			Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
			enrollmentId.setUserId(guest.getUserId());
			enrollmentId.setCourseId(course.getCourseID());
			Enrollment enrollment = new Enrollment();
			enrollment.setId(enrollmentId);
			enrollment.setUser(guest);
			enrollment.setAccessType(AccessType.ACCEPT);
			enrollment.setCourse(course);
			enrollmentRepository.save(enrollment);
		}
	}
}
