// Event onchange cho input image
$("#img").on('change', function(event){
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function() {
        $("#imgPreview").attr("src", reader.result);
    };
    reader.readAsDataURL(file);
})