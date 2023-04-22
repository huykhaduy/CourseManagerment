# CourseManagerment
Quản lý khóa học của trung tâm tin học
- Entity: dùng để tự động tạo các thực thể trong database
- Repository: tương tự như lớp DAO(data access layer) dùng để truy cập dữ liệu từ database và mapping vào DTO
- DTO (Data Transfer Object): dùng để lưu dữ liệu của các đối tượng để truy xuất ở Controller và các tầng cao hơn. Không những đó nó giúp cho website bảo mật hơn, tránh việc tạo ra các dư thừa dữ liệu.
- Services: là lớp xử lý logic hệ thống, tương tự BUS. Nó sẽ lấy các entity dưới database nhờ Repository và trả về các DTO.
- View: dùng để hiển thị dữ liệu ra cho người dùng, thường là các file html,css,js
- Controller: dùng để đẩy dữ liệu đã xử lý logic ở lớp Services vào model tương ứng. Ngoài ra controller có thể trả về các rest api mà không cần đổ vào bất kì view nào.

Mô tả hệ thống: Trang website quản lý khóa học sẽ bao gồm 3 loại người dùng chính (admin, giảng viên, học viên), tuy nhiên tạm thời admin sẽ được làm cuối cùng (tạm bỏ qua). 
- Giảng viên có thể tạo ra và quản lý các khóa học, giảng viên có thể thêm các học viên vào khóa học (nếu học viên chưa có tài khoản, hệ thống sẽ tự động gửi email cho học viên). Trong các khóa học, quản trị viên có thể tạo ra các chương (Lesson) và thêm video hoặc soạn thảo nội dung cho học viên. Ngoài ra, giảng viên có thể upload lên các file tài liệu học tập cho người dùng. Giảng viên có thể thêm các bài tập và xem nội dung mà học viên nộp. Trong các course sẽ có hội thoại để thảo luận học tập. 
- Học viên ban đầu sẽ không có bất kì khóa học nào. Học viên có thể nhập mã khóa học hoặc được giảng viên mời vào nhóm. Học viên chỉ có thể xem các khóa học mà bản thân được tham dự. Học viên có thể xem nội dung của các chương trong khóa học. Tham gia thảo luận trong khóa học. Nộp các bài tập cho khóa học.
https://techmaster.vn/posts/36586/spring-security-authorization
