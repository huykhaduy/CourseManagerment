const modalAddCourse = $("#addCourseModal")[0];

const fetchCourse = (text) =>{
    $.ajax({
        url: "/course/owned",
        method: "GET",
        data: {
            text: text
        },
        success: function(res){
            let html = '';
            let number = 0;
            if (res){
                number = res.length;
                html = res.map(item=>{
                    return `
<div class="row course-item py-3">
    <div class="col-1">
        <img src="${item.courseAvt}" alt="">
    </div>
    <div class="col-3">
        ${item.courseID}
    </div>
    <div class="col-2">
        ${item.courseName}
    </div>
    <div class="col-2">
        ${item.courseDes}
    </div>
    <div class="col-1">
        ${item.createDate}
    </div>
    <div class="col-2">
        ${item.department.departmentName}
    </div>
    <div class="col-1 text-center">
        <div class="dropdown">
            <button class="btn dropdown-toggle" type="button" id="triggerId" data-bs-toggle="dropdown"
                    aria-haspopup="true"
                    aria-expanded="false">
                <i class="bi bi-three-dots-vertical"></i>
            </button>
            <div class="dropdown-menu" aria-labelledby="triggerId">
                <button class="dropdown-item" data-bs-toggle="modal" data-bs-target="#editStudent">Thêm chapter
                </button>
                <button class="dropdown-item">Sửa khóa học</button>
                <button class="dropdown-item">Xóa khóa học</button>
                <button class="dropdown-item" data-bs-toggle="modal" data-bs-target="#editStudent">Thông tin học
                    viên
                </button>
            </div>
        </div>
    </div>
</div>
                    `
                })
            }
            $("#course-container").html(html);
            $("#course-number").html(number);
        }
    })
}
const fetchDepartment = () => {
    $.ajax({
        url: "/course/departments",
        method: "GET",
        success: function (data) {
            $("#departmentId").html("");
            let isFirst = true;
            if (data){
                let modalAddDepartments = data.map(item => {
                    if (isFirst === true){
                        isFirst = false;
                        return `<option value="${item.departmentId}" selected>${item.departmentName}</option>`
                    }
                    else {
                        return `<option value="${item.departmentId}">${item.departmentName}</option>`
                    }

                })
                $("#departmentId").html(modalAddDepartments);
            }
        }
    })
}

$('#add-course-form').submit(function(event) {
    event.preventDefault();
    let formData = new FormData();
    formData.append("courseAvt", $("#courseAvt")[0].files[0])
    formData.append("courseName", $("#courseName").val())
    formData.append("courseDes", $("#courseDes").val())
    formData.append("departmentId", $("#departmentId").val())
    $.ajax({
        url: '/course/add',
        type: 'POST',
        data: formData,
        dataType: 'json',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function(response) {
            // handle success response
            console.log(response);
        },
        error: function(xhr, status, error) {
            // handle error response
        }
    });
});

$("#course-search").on('change', function(){
    fetchCourse($("#course-search").val());
})

fetchCourse();
fetchDepartment();