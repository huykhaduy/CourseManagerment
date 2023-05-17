// Event onchange cho input image
$("#img").on('change', function(event){
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function() {
        $("#imgPreview").attr("src", reader.result);
    };
    reader.readAsDataURL(file);
})

$('#formAddChapter').submit(function(event) {
    event.preventDefault();
    let formData = new FormData();
    let file = $("#chapterVideo")[0].files[0];
    if (file)
        formData.append("chapterVideoMulti", file);
    formData.append("chapterTitle", $("#chapterTitle").val())
    formData.append("chapterContent", $("#chapterContent").val())
    formData.append("youtubeUrl", $("#youtubeUrl").val())
    formData.append("courseId", $("#courseId").val());
    $.ajax({
        url: '/course/chapter/add',
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
                title: 'Khóa học đã được thêm',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi thêm!', '', 'error');
        }
    });
});

const openEditModal = (chapterId) => {
    $('#editChapterModal').modal('show');
    $.ajax({
        url: `/course/chapter/get/${chapterId}`,
        method: 'GET',
        success: function (res){
            $("#editChapterId").val(res.chapterId);
            $("#editChapterTitle").val(res.chapterTitle);
            $("#editYoutubeUrl").val(res.chapterVideo);
            $("#editChapterContent").val(res.chapterContent);
        }
    });
}

$('#formEditChapter').submit(function(event) {
    event.preventDefault();
    let formData = new FormData();
    let file = $("#editChapterVideo")[0].files[0];
    if (file)
        formData.append("chapterVideoMulti", file);
    formData.append("chapterTitle", $("#editChapterTitle").val())
    formData.append("chapterContent", $("#editChapterContent").val())
    formData.append("youtubeUrl", $("#editYoutubeUrl").val())
    formData.append("chapterId", $("#editChapterId").val());
    $.ajax({
        url: '/course/chapter/edit',
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
                title: 'Khóa học đã được sửa thành công',
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

const removeChapter = (chapterId) =>{
    Swal.fire({
        title: 'Bạn muốn xóa nội dung học này ?',
        showDenyButton: true,
        confirmButtonText: 'Có',
        denyButtonText: `Không`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            $.ajax({
                url: `/course/chapter/remove/${chapterId}`,
                method: "POST",
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

const openFileModal = () =>{
    $('#addFileCourse').modal('show');
    let html = ``;
    let isFirst = true;
    $.ajax({
        url: `/file/self`,
        method: 'GET',
        success: function (res){
            html = res.map(item=>{
                return `<option value="${item.fileID}">${item.fileName}</option>`
            }).join(' ');
            $("#selectFiles").html(html);
        }
    });
}

$("#btnAddFile").click(function(){
    let courseId = $("#courseId").val();
    $.ajax({
        url: `/course/file/add2/${courseId}`,
        type: 'POST',
        data: {
            fileId: $("#selectFiles").val(),
        },
        dataType: 'json',
        success: function(response) {
            // handle success response
            console.log(response);
            Swal.fire({
                icon: 'success',
                title: 'Tài liệu được thêm thành công',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi thêm!', '', 'error');
        }
    });
})

$("#fileId").change(function(event){
    let formData = new FormData();
    let file = $("#fileId")[0].files[0];
    let courseId = $("#courseId").val();
    if (file){
        formData.append("fileUpload", file);
    }
    $.ajax({
        url: `/course/file/add/${courseId}`,
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
                title: 'Đã thêm tài liệu thành công',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi thêm!', '', 'error');
        }
    });
})

const removeFiles = (fileId) =>{
    let courseId = $("#courseId").val();
    Swal.fire({
        title: 'Bạn muốn xóa tài liệu này ?',
        showDenyButton: true,
        confirmButtonText: 'Có',
        denyButtonText: `Không`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            $.ajax({
                url: `/course/file/remove/${courseId}`,
                method: "POST",
                data: {
                    fileId: fileId
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