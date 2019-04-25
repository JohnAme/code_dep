function selectFile() {
    $('#file').trigger("click");
}

function check() {
    var objFile = document.getElementById("file");
    if (objFile.value == "") {
        alert("不能为空空");
        return false;
    }
    console.log(objFile.files[0].size); // 文件字节数
    var files = $('#file').prop('files');//获取到文件列表
    if (files.length == 0) {
        alert('请选择文件');
    } else {
        var reader = new FileReader();//新建一个FileReader
        reader.readAsText(files[0], "UTF-8");//读取文件
        // reader.readAsBinaryString(files[0]);//读取文件
        reader.onload = function (evt) { //读取完文件之后会回来这里
            var fileString = evt.target.result; // 读取文件内容
            document.getElementById("box").innerText = fileString;
        }
    }
}