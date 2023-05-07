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

