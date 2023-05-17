const removeMember = (memberId) =>{
    let courseId = $("#courseId").val();
    Swal.fire({
        title: 'Bạn muốn xóa học viên này ?',
        showDenyButton: true,
        confirmButtonText: 'Có',
        denyButtonText: `Không`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            $.ajax({
                url: `/course/members/${courseId}/remove`,
                method: "POST",
                data: {
                    userId: memberId,
                },
                success: function (){
                    Swal.fire('Đã xóa thành công!', '', 'success').then(()=> location.reload());
                },
                error: function (){
                    Swal.fire('Có lỗi khi xóa!', '', 'error');
                }
            })
        }
    })
}

const acceptMember = (memberId) => {
    let courseId = $("#courseId").val();
    $.ajax({
        url: `/course/members/${courseId}/add`,
        method: "POST",
        data: {
            userId: memberId,
        },
        success: function (){
            Swal.fire('Đã chấp nhận học viên!', '', 'success').then(()=> location.reload());
        },
        error: function (){
            Swal.fire('Có lỗi khi thực hiện!', '', 'error');
        }
    })
}

$("#searchBtn").click(function (event){
    event.preventDefault();
    $.ajax({
        url: `/user/allStudents`,
        method: "GET",
        data: {
            search: $("#studentInput").val(),
        },
        success: function (res){
            let html = "";
            html = res.map(item=>{
                return `<div class="user-item-search" onclick="addUserToCourse('${item.userId}')">
                               ${item.fullName}(${item.email})
                         </div>`
            }).join(' ');
            $("#list-user").html(html);
        },
        error: function (){
            Swal.fire('Có lỗi khi thực hiện!', '', 'error');
        }
    })
})

const addUserToCourse = (memberId) => {
    let courseId = $("#courseId").val();
    $.ajax({
        url: `/course/members/${courseId}/add`,
        method: "POST",
        data: {
            userId: memberId,
        },
        success: function (res){
            Swal.fire('Đã thêm học viên thành công!', '', 'success').then(()=> location.reload());
        },
        error: function (){
            Swal.fire('Có lỗi khi thực hiện!', '', 'error');
        }
    })
}