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
		initializeDB();
	}

//	@Transactional
//	public void initializeData(){
//		// Tạo các role
//		Role role = new Role();
//		role.setRoleId(1);
//		role.setRoleName("ROLE_ADMIN");
//
//		Role role2 = new Role();
//		role2.setRoleId(2);
//		role2.setRoleName("ROLE_STUDENT");
//
//		Role role3 = new Role();
//		role3.setRoleId(3);
//		role3.setRoleName("ROLE_TEACHER");
//		roleRepository.save(role);
//		roleRepository.save(role2);
//		roleRepository.save(role3);
//
//		// Tạo user
//		User adminUser = new User();
//		adminUser.setUserId(1L);
//		adminUser.setFullName("Duy Huynh");
//		adminUser.setEmail("duy@gmail.com");
//		adminUser.setPassword(passwordEncoder.encode("123456"));
//		adminUser.getListRoles().add(role);
//		role.getListUsers().add(adminUser);
//		userRepository.save(adminUser);
//		roleRepository.save(role);
//
//		User guest = new User();
//		guest.setUserId(2L);
//		guest.setFullName("Guest");
//		guest.setEmail("duy2@gmail.com");
//		guest.setPassword(passwordEncoder.encode("123456"));
//		guest.getListRoles().add(role2);
//		role2.getListUsers().add(guest);
//		userRepository.save(guest);
//		roleRepository.save(role2);
//
//		Department cntt = new Department();
//		cntt.setDepartmentId(1);
//		cntt.setDepartmentName("Công nghệ thông tin");
//		departmentRepository.save(cntt);
//
//		Department laptrinh = new Department();
//		laptrinh.setDepartmentId(2);
//		laptrinh.setDepartmentName("Điện tử viễn thông");
//		departmentRepository.save(laptrinh);
//		// Tạo các course
//		for (int i=0;i<20;i++){
//			Course course = new Course();
//			course.setCourseOwner(adminUser);
//			course.setCourseDes("Mô tả khóa học Contrary to popular belief, Lorem Ipsum is not simply random text.");
//			course.setCourseName("Khóa học thứ "+i);
//			if (i%2==0)
//				course.setDepartment(cntt);
//			else
//				course.setDepartment(laptrinh);
//			courseRepository.save(course);
//
//			for (int j=0;j<5;j++){
//				Chapter chapter = new Chapter();
//				chapter.setChapterTitle(j+".Xin chào thế giới");
//				chapter.setChapterSort(j);
//				chapter.setChapterVideo("https://www.youtube.com/embed/z2f7RHgvddc");
//				chapter.setChapterContent("Nội dung chương Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.");
//				chapter.setCourse(course);
//				chapterRepository.save(chapter);
//			}
//			Notify newNotify = new Notify();
//			newNotify.setNotiTitle("Xin chào course");
//			newNotify.setCourse(course);
//
//			Invite invite1 = new Invite();
//			invite1.setInviteEmail("du@gmail.com");
//			invite1.setCourse(course);
//			notifyRepository.save(newNotify);
//			inviteRepository.save(invite1);
//
//			Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
//			enrollmentId.setUserId(guest.getUserId());
//			enrollmentId.setCourseId(course.getCourseID());
//			Enrollment enrollment = new Enrollment();
//			enrollment.setId(enrollmentId);
//			enrollment.setUser(guest);
//			enrollment.setAccessType(AccessType.ACCEPT);
//			enrollment.setCourse(course);
//			enrollmentRepository.save(enrollment);
//		}
//	}
	@Transactional
	public void initializeDB(){
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

		Department nna = new Department();
		nna.setDepartmentId(2);
		nna.setDepartmentName("Ngôn ngữ Anh");
		departmentRepository.save(nna);

		Department dtvt = new Department();
		dtvt.setDepartmentId(3);
		dtvt.setDepartmentName("Điện tử viễn thông");
		departmentRepository.save(dtvt);

		Department toan = new Department();
		toan.setDepartmentId(4);
		toan.setDepartmentName("Toán ứng dụng");
		departmentRepository.save(toan);

		for (int i = 0 ;i<5;i++){
			Course cslt = new Course();
			cslt.setCourseAvt("https://files.fullstack.edu.vn/f8-prod/courses/7.png");
			cslt.setCourseOwner(adminUser);
			cslt.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			cslt.setCourseName("Cơ sở lập trình thứ "+i);
			cslt.setDepartment(cntt);
			courseRepository.save(cslt);

			Enrollment.EnrollmentId enrollmentId = new Enrollment.EnrollmentId();
			enrollmentId.setUserId(guest.getUserId());
			enrollmentId.setCourseId(cslt.getCourseID());
			Enrollment enrollment = new Enrollment();
			enrollment.setId(enrollmentId);
			enrollment.setUser(guest);
			enrollment.setAccessType(AccessType.ACCEPT);
			enrollment.setCourse(cslt);
			enrollmentRepository.save(enrollment);

			Chapter chapter1 = new Chapter();
			chapter1.setChapterTitle("1. Khái niệm, kỹ thuật cần biết");
			chapter1.setChapterSort(1);
			chapter1.setChapterVideo("https://www.youtube.com/embed/zoELAirXMJY");
			chapter1.setChapterContent("Mô hình Client - Server là mô hình được sử dụng để triển khai các trang web hiện nay. Toàn bộ các trang web mà bạn có thể truy cập đều đang sử dụng mô hình này. Hãy cùng tìm hiểu tổng quan về mô hình Client - Server trước khi bắt đầu vào các khóa học về lập trình web bạn nhé.");
			chapter1.setCourse(cslt);
			chapterRepository.save(chapter1);

			Chapter chapter2 = new Chapter();
			chapter2.setChapterTitle("2. Domain là gì");
			chapter2.setChapterSort(2);
			chapter2.setChapterVideo("https://www.youtube.com/embed/M62l1xA5Eu8");
			chapter2.setChapterContent("Domain hay còn gọi là \"Tên miền\", đây là kiến thức các bạn có thể bắt đầu tìm hiểu trước khi đi vào các bài học chuyên sâu (phải làm quen với code).");
			chapter2.setCourse(cslt);
			chapterRepository.save(chapter2);

			Chapter chapter3 = new Chapter();
			chapter3.setChapterTitle("3. Phương hướng học lập trình");
			chapter3.setChapterSort(3);
			chapter3.setChapterVideo("https://www.youtube.com/embed/DpvYHLUiZpc");
			chapter3.setChapterContent("Bạn cần có mục tiêu cho việc làm của mình, học lập trình cũng vậy, từ đó bạn sẽ lên được lộ trình học lập trình và phương pháp học lập trình phù hợp với bản thân hơn. Nếu không có mục tiêu thì bạn sẽ không thể tìm ra được lộ trình học lập trình đâu nhé.\n" +
					"\n" +
					"Tiếp theo là sự chủ động, nếu như ngày học phổ thông thì hầu hết chúng ta có tư tưởng là \"phải\" học. Học lập trình nó khác, phần lớn các bạn đều mong muốn học lập trình để đi làm và kiếm nhiều tiền nên mỗi lúc có thời gian hãy tự chủ động học và suy nghĩ về nó nhé. Bây giờ lớn rồi! Đừng để ai phải nhắc cho tương lai của chính mình nhé!.");
			chapter3.setCourse(cslt);
			chapterRepository.save(chapter3);


			Course laptrinhc = new Course();
			laptrinhc.setCourseOwner(adminUser);
			laptrinhc.setCourseAvt("https://files.fullstack.edu.vn/f8-prod/courses/21/63e1bcbaed1dd.png");
			laptrinhc.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			laptrinhc.setCourseName("Lập trình c cơ bản, nâng cao thứ "+ i);
			laptrinhc.setDepartment(cntt);
			courseRepository.save(laptrinhc);

			Enrollment.EnrollmentId enrollmentId2 = new Enrollment.EnrollmentId();
			enrollmentId2.setUserId(guest.getUserId());
			enrollmentId2.setCourseId(laptrinhc.getCourseID());
			Enrollment enrollment2 = new Enrollment();
			enrollment2.setId(enrollmentId2);
			enrollment2.setUser(guest);
			enrollment2.setAccessType(AccessType.ACCEPT);
			enrollment2.setCourse(laptrinhc);
			enrollmentRepository.save(enrollment2);

			Chapter chapterc1 = new Chapter();
			chapterc1.setChapterTitle("1. Tổng quan về khóa học Lập trình C++");
			chapterc1.setChapterSort(1);
			chapterc1.setChapterVideo("https://www.youtube.com/embed/WS05AU6YYm4");
			chapterc1.setChapterContent("Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.\n" +
					"\n" +
					"Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).\n" +
					"\n" +
					"Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp");
			chapterc1.setCourse(laptrinhc);
			chapterRepository.save(chapterc1);

			Chapter chapterc2 = new Chapter();
			chapterc2.setChapterTitle("2. Biến trong C++");
			chapterc2.setChapterSort(2);
			chapterc2.setChapterVideo("https://www.youtube.com/embed/i3nJyEt42Y8");
			chapterc2.setChapterContent("Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.\n" +
					"\n" +
					"Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).\n" +
					"\n" +
					"Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp");
			chapterc2.setCourse(laptrinhc);
			chapterRepository.save(chapterc2);

			Chapter chapterc3 = new Chapter();
			chapterc3.setChapterTitle("3. Vòng lặp For trong C++");
			chapterc3.setChapterSort(3);
			chapterc3.setChapterVideo("https://www.youtube.com/embed/aL59MpOFMe0");
			chapterc3.setChapterContent("Ngôn ngữ lập trình C++ là một ngôn ngữ lập trình hướng đối tượng(OOP – Object-oriented programming) được phát triển bởi Bjarne Stroustrup. C++ là ngôn ngữ lập trình được phát triển trên nên tảng của ngôn ngữ lập trình C. Do đó, C++ có song song cả 2 phong cách(style) lập trình hướng cấu trúc giống C và có thêm phong cách hướng đối tượng. Trong nhiều trường hợp, C++ sử dụng kết hợp cả 2 style trên. Do đó, nó được xem là một ngôn ngữ “lai tạo”.\n" +
					"\n" +
					"Ngôn ngữ C++ là một ngôn ngữ lập trình cấp trung. Bởi vì nó có các tính chất của cả ngôn ngữ lập trình bậc thấp(Pascal, C…) và ngôn ngữ lập trình bậc cao(C#, Java, Python…).\n" +
					"\n" +
					"Ngôn ngữ lập trình C++(C plus plus) có đuôi mở rộng là .cpp");
			chapterc3.setCourse(laptrinhc);
			chapterRepository.save(chapterc3);

			Course winform = new Course();
			winform.setCourseAvt("https://static.skillshare.com/uploads/discussion/tmp/b8ba300b.png");
			winform.setCourseOwner(adminUser);
			winform.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			winform.setCourseName("Lập trình winform thứ"+i);
			winform.setDepartment(cntt);
			courseRepository.save(winform);

			Enrollment.EnrollmentId enrollmentId3 = new Enrollment.EnrollmentId();
			enrollmentId3.setUserId(guest.getUserId());
			enrollmentId3.setCourseId(winform.getCourseID());
			Enrollment enrollment3 = new Enrollment();
			enrollment3.setId(enrollmentId3);
			enrollment3.setUser(guest);
			enrollment3.setAccessType(AccessType.ACCEPT);
			enrollment3.setCourse(winform);
			enrollmentRepository.save(enrollment3);

			Chapter winform1 = new Chapter();
			winform1.setChapterTitle("1. Tổng quan lập trình Winform");
			winform1.setChapterSort(1);
			winform1.setChapterVideo("https://www.youtube.com/embed/dtYVRWfGhzI");
			winform1.setChapterContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform1.setCourse(winform);
			chapterRepository.save(winform1);

			Chapter winform2 = new Chapter();
			winform2.setChapterTitle("2. Panel và FlowLayoutPanel");
			winform2.setChapterSort(2);
			winform2.setChapterVideo("https://www.youtube.com/embed/Cljvl3ur1wg");
			winform2.setChapterContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform2.setCourse(winform);
			chapterRepository.save(winform2);

			Chapter winform3 = new Chapter();
			winform3.setChapterTitle("3. Textbox trong winform");
			winform3.setChapterSort(3);
			winform3.setChapterVideo("https://www.youtube.com/embed/MsSds2bDqKA");
			winform3.setChapterContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			winform3.setCourse(winform);
			chapterRepository.save(winform3);

			Course dientu = new Course();
			dientu.setCourseAvt("https://codelearn.io/Upload/Blog/nganh-dien-tu-vien-thong-hoc-gi-63729858518.6825.jpg");
			dientu.setCourseOwner(adminUser);
			dientu.setCourseDes("Để có cái nhìn tổng quan về ngành IT - Lập trình web các bạn nên xem các videos tại khóa này trước nhé.");
			dientu.setCourseName("Điện tử và điện tử số "+i);
			dientu.setDepartment(dtvt);
			courseRepository.save(dientu);

			Chapter dientu1 = new Chapter();
			dientu1.setChapterTitle("1. Tổng quan lập trình Winform");
			dientu1.setChapterSort(1);
			dientu1.setChapterVideo("https://www.youtube.com/embed/dtYVRWfGhzI");
			dientu1.setChapterContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu1.setCourse(dientu);
			chapterRepository.save(dientu1);

			Chapter dientu2 = new Chapter();
			dientu2.setChapterTitle("2. Panel và FlowLayoutPanel");
			dientu2.setChapterSort(2);
			dientu2.setChapterVideo("https://www.youtube.com/embed/Cljvl3ur1wg");
			dientu2.setChapterContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu2.setCourse(dientu);
			chapterRepository.save(dientu2);

			Chapter dientu3 = new Chapter();
			dientu3.setChapterTitle("3. Textbox trong winform");
			dientu3.setChapterSort(3);
			dientu3.setChapterVideo("https://www.youtube.com/embed/MsSds2bDqKA");
			dientu3.setChapterContent("Windows Forms là thư viện lớp đồ họa mã nguồn mở và miễn phí được bao gồm như một phần của Microsoft.NET Framework hoặc Mono Framework, cung cấp nền tảng để viết các ứng dụng khách phong phú cho máy tính để bàn, máy tính xách tay và máy tính bảng");
			dientu3.setCourse(dientu);
			chapterRepository.save(dientu3);
		}
	}
}
