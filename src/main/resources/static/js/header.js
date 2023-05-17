$("#input-search-box").on('input',(function(){
    let inputValue =  $("#input-search-box").val();
    if (inputValue !== ""){
        $("#popup-search-box").css("display", "none");
        $("#list-data-search").html("");
        $.ajax({
            url: "/course/search",
            method: "GET",
            data:{
                text: inputValue
            },
            success: function(data) {
                if (!data.length) return;

                $("#popup-search-box").css("display", "block");
                let html = data.map(item=>{
                    return `<li class="list-item">
                                <a href="/detail/${item.courseID}" class="d-flex align-items-center">
                                    <img class="search-item-img" src="${item.courseAvt}">
                                    <div class="search-item-name text-dark mx-2">${item.courseName}</div>
                                </a>
                            </li>`
                }).join(" ");
                $("#list-data-search").html(html);
            },
            fail: function(){
                console.log("fail");
            }
        });
    }
    else {
        $("#popup-search-box").css("display", "none");
    }
}));


$("#input-admin-search-box").on('input',(function(){
    let inputValue =  $("#input-admin-search-box").val();
    if (inputValue !== ""){
        $("#popup-admin-search-box").css("display", "none");
        $("#list-admin-data-search").html("");
        $.ajax({
            url: "/course/owned",
            method: "GET",
            data:{
                text: inputValue
            },
            success: function(data) {
                if (!data.length) return;

                $("#popup-admin-search-box").css("display", "block");
                let html = data.map(item=>{
                    return `<li class="list-item">
                                <a href="/admin/detail/${item.courseID}" class="d-flex align-items-center">
                                    <img class="search-item-img" src="${item.courseAvt}">
                                    <div class="search-item-name text-dark mx-2">${item.courseName}</div>
                                </a>
                            </li>`
                }).join(" ");
                $("#list-admin-data-search").html(html);
            },
            fail: function(){
                console.log("fail");
            }
        });
    }
    else {
        $("#popup-admin-search-box").css("display", "none");
    }
}));
var btnEdit = document.getElementById("btn-edit");
var btnChange = document.getElementById("btn-change");
var editForm = document.getElementById("edit-form");
var changeForm = document.getElementById("change-form");

btnEdit.addEventListener("click", function() {
    if (changeForm.classList.contains("show")) {
        changeForm.classList.remove("show");
    }
});

btnChange.addEventListener("click", function() {
    if (editForm.classList.contains("show")) {
        editForm.classList.remove("show");
    }
});


