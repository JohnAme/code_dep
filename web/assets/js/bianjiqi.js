
var canvas = new fabric.Canvas('c');
canvas.setWidth(452);
canvas.setHeight(452);

function justAdd(file) {
    if (!file.files || !file.files[0]) {
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        document.getElementById('putimage').src = evt.target.result;
        fabric.Image.fromURL(evt.target.result,function(oImg) {
            // oImg.scale(1).set({ width:452 , height:452});

            // canvas.setBackgroundImage(oImg,canvas.renderAll.bind(canvas));
            canvas.add(oImg);
            canvas.sendToBack(oImg);
        });
    };
    reader.readAsDataURL(file.files[0]);
}

function add() {
    addRed();
    addBlue();
    addGreen();
}

function addRed() {
    var red = new fabric.Rect({
        top: 100, left: 0, width: 80, height: 50, fill: 'red' });
    canvas.add(red);
}

function addBlue() {
    var blue = new fabric.Rect({
        top: 0, left: 100, width: 50, height: 70, fill: 'blue' });
    canvas.add(blue);
}

function addGreen() {
    var green = new fabric.Rect({
        top: 100, left: 100, width: 60, height: 60, fill: 'green' });
    canvas.add(green);
}

function selectImage(file) {
    if (!file.files || !file.files[0]) {
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        document.getElementById('putimage').src = evt.target.result;
        fabric.Image.fromURL(evt.target.result,function(oImg) {
            // oImg.scale(1).set({ width:452 , height:452});

            // canvas.setBackgroundImage(oImg,canvas.renderAll.bind(canvas));
            canvas.add(oImg);
            canvas.sendToBack(oImg);
        });
    };
    reader.readAsDataURL(file.files[0]);

}

function addImage(elementId){
    var imgElement = document.getElementById(elementId);
    var imgInstance = new fabric.Image(imgElement, {
        left: 100,
        top: 100,
        angle: 0,
        opacity: 1
    });
    canvas.add(imgInstance);

    // fabric.Image.fromURL('my_image.png', function(oImg) {
    //     canvas.add(oImg);
    // });
    //
    // fabric.Image.fromURL('my_image.png', function(oImg) {
    //     // scale image down, and flip it, before adding it onto canvas
    //     oImg.scale(0.5).setFlipX(true);
    //     canvas.add(oImg);
    // });
}

function submit() {
    // window.event.returnValue=false;
    // var oCanvas = document.getElementById('c');
    // document.getElementById('putimage').src = canvas.toDataURL("image/png");
    // document.getElementById('demowork').src = canvas.toDataURL("image/png");
    window.location.href = "user.php";
}



function imgPreview(fileDom) {
    //判断是否支持FileReader
    if(window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }
    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if(!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function(e) {
        //获取图片dom
        var img = document.getElementById("preview");
        //图片路径设置为读取的图片

        img.src = e.target.result;
    };
    reader.readAsDataURL(file);
}

// var formData = new FormData();
// formData.append('file', $('#input_file')[0].files[0]);  //添加图片信息的参数
// formData.append('sizeid',123);  //添加其他参数
// $.ajax({
//     url: '/material/uploadFile',
//     type: 'POST',
//     cache: false, //上传文件不需要缓存
//     data: formData,
//     processData: false, // 告诉jQuery不要去处理发送的数据
//     contentType: false, // 告诉jQuery不要去设置Content-Type请求头
//     success: function (data) {
//         var rs = eval("("+data+")");
//         if(rs.state==1){
//             alert('上传成功！');
//         }else{
//             alert(rs.msg);
//         }
//     },
//     error: function (data) {
//         alert("上传失败");
//     }
// });

function preloadImg() {
    $.ajax({
        url: "",
        type: "GET",
        data: {

        },
        success: function (result) {
            result.forEach(function (value) {
                $('#imgView').append("<div class=\"previewImg\">\n" +
                    "<img id=\"preview\" src=\"" + value +">\n" +
                    "</div>\n");
            })
        }
    });
}

$().ready(function () {
    console.log("success");
    preloadImg();
});