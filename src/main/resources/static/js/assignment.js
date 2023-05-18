$('#btnAddAssignment').click(function(event) {
    event.preventDefault();
    let formData = new FormData();
    // let file = $("#assignFile")[0].files[0];
    // if (file)
    //     formData.append("assignMultiFile", file);
    formData.append("assignTitle", $("#assignmentTitle").val())
    formData.append("assignDes", $("#assignmentDes").val())
    const fp = document.querySelector("#datetimeDeadline")._flatpickr;
    if (fp.selectedDates.length > 0) {
        let pickDate = new Date(fp.selectedDates);
        formData.append("deadline", pickDate.toISOString());
    }
    formData.append("courseId", $("#courseId").val());
    $.ajax({
        url: '/assignment/add',
        type: 'POST',
        data: formData,
        dataType: 'json',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function(response) {
            // handle success response
            console.log(response);
            Swal.fire({
                icon: 'success',
                title: 'Bài tập đã được tạo',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi tạo!', '', 'error');
        }
    });
});

function openEditModal(assignId) {
    $('#edit_modal').modal('show');
    $.ajax({
        url: `/assignment/get/${assignId}`,
        method: 'GET',
        success: function (res){
            $("#editAssignmentId").val(res.assignId);
            $("#editAssignmentTitle").val(res.assignTitle);
            // $("#editA").val(res.chapterVideo);
            $("#editAssignmentDes").val(res.assignDes);
            const fp = flatpickr("#editDeadline", {
                enableTime: true,
                dateFormat: "d-m-Y H:i",
                minDate: "today",
                confirmIcon: "<i class='fa fa-check'></i>", // your icon's html, if you wish to override
                confirmText: "OK ",
                showAlways: false,
                theme: "light" // or "dark"
            }); // flatpickr
            let a = fp.parseDate(res.deadline);
        }
    });
}

$('#formEdit').submit(function(event) {
    event.preventDefault();
    let formData = new FormData();
    // let file = $("#assignFile")[0].files[0];
    // if (file)
    //     formData.append("assignMultiFile", file);
    formData.append("assignTitle", $("#editAssignmentTitle").val())
    formData.append("assignDes", $("#editAssignmentDes").val())
    formData.append("courseId", $("#courseId").val());
    $.ajax({
        url: '/assignments/edit',
        type: 'PUT',
        data: formData,
        dataType: 'json',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function(response) {
            // handle success response
            console.log(response);
            Swal.fire({
                icon: 'success',
                title: 'Bài tập đã được sửa',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi sửa!', '', 'error');
        }
    });
});

const deleteAssignment = (AssignId) =>{
    Swal.fire({
        title: 'Bạn muốn xóa nội dung học này ?',
        showDenyButton: true,
        confirmButtonText: 'Có',
        denyButtonText: `Không`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            $.ajax({
                url: `/assignment/delete/${AssignId}`,
                method: "Delete",
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