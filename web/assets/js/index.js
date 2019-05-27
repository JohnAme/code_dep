var curRow = 0;
var curRowIndex = 1;

var line1 = $('#tr1');
line1.on('click',function () {
    $(this).css('background-color','red');
});

var line2 = $('#tr2');
line2.on('click',function () {
    $(this).css('background-color','green');
    alert("1");
});

var json = [
    {"class":"FileController","score":"0.150265486","isVerified":"","effect":""},
    {"class":"ManageService","score":"0.211244230","isVerified":"","effect":""},
    {"class":"LoginServlet","score":"0.588179880","isVerified":"","effect":""},
    {"class":"EditHealth","score":"0.648066566","isVerified":"","effect":""},
    {"class":"StorageService","score":"0.314593012","isVerified":"Irrelevant","effect":""},
    {"class":"TempUcUtil","score":"0.477558188","isVerified":"","effect":""},
    {"class":"StartController","score":"0.478028838","isVerified":"","effect":""},
    {"class":"Application","score":"0.681305107","isVerified":"Relevant","effect":""},

    ];

var recentProjects = [
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""},
    {"Project Name":"test_1.0_SNAPSHOT.jar","Operation Time":"2019-04-03 16:03:25","Operate":""}
];

$(function(){
    $("#loginSubmit").click(function () {
        var url = "loginServlet";
        var username = $("input[id='loginUsername']").val();
        var params = {
            "username":username,
            "password":$("input[id='loginPassword']").val()
        };
        $.ajax({
            "url" : url,
            "dataType": "json",
            "data" : params,
            "type" : "post",
            "success" : function(data) {
                // 参数为json类型的对象
                alert(data.message);
                var username = params.username;
                // var text = "<a href=\"#\" class=\"navbar-brand\">Hello," + username + "</a>";
                // alert("hello," + username );
                // document.getElementById("nav_register").style.display = "none";
                // $("#nav_register").style.display="none";
            },
            "error" : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("用户名或密码不正确");
                // alert(XMLHttpRequest.status);
                // alert(XMLHttpRequest.readyState);
                // alert(textStatus);
            }
        });
        setCookie("username",username,1);
        // setCookie("username", ' ', -1);
    });

    $("#downloadSubmit").click(function () {
        var url = "searchServlet";
        var params = {
            "url" : $("input[id='downloadUrl']").val()
        };
        $.ajax({
            "url" : url,
            "dataType" : "json",
            "data" : params,
            "type" :"post",
            "success" : function(data) {
                // 参数为json类型的对象
                alert(data.message);
            },
            "error" : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("已下载～");
            }
        })
    });

});

// $("#login_modal").on('hidden.bs.modal',function(){
//     alert(1);
//     $("#test").hide();
// });

// $("#login_modal").on('hide.bs.modal',function(){
//     alert(2);
//     $("#test").hide();
// });

/*初始化table数据*/
$("#sth").bootstrapTable({
    columns: [{
        field: "class",
        title: 'class',
    }, {
        field: 'score',
        title: 'score'
    }, {
        field: 'effect',
        title: 'effect'
    },{
        field: 'isVerified',
        title: 'isVerified',
        align: 'center',
        // events: window.operateEvents,
        // formatter: operateFormatter
    }],
    data: json,
    sortable: true,
    sortOrder: "desc",
    sortName: 'isVerified',
    sortName: 'score',//排序字段
    rowStyle: function (row, index) {
        //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
        var strclass = "";
        if (row.isVerified == "Relevant") {
            strclass = 'success';//还有一个active
        }
        else if (row.isVerified == "Irrelevant") {
            strclass = 'danger';
        }
        else {
            return {};
        }
        return { classes: strclass }
    },
    onDblClickRow: function (row, element, field) {
        var modal = $("#verify");
        modal.modal();
        $("#verifyClass").html("class : " + row.class);
        curRow = row;
        curRowIndex = element.data('index');
    },

    // onClickRow: function (row,$element,field) {
    //     $($element).css('background-color','#65e849');
    // }
});

// function operateFormatter(value, row, index) {
//     return [
//         '<a class="like" href="javascript:void(0)" title="Like">',
//         '<i class="glyphicon glyphicon-heart"></i>',
//         '</a>',
//         '<a class="remove" href="javascript:void(0)" title="Remove">',
//         '<i class="glyphicon glyphicon-remove"></i>',
//         '</a>'
//     ].join('')
// }
//
//
// window.operateEvents = {
//     'click .like': function (e, value, row, index) {
//         // alert('You click like action, row: ' + JSON.stringify(row));
//         // $("tr[data-index=sindex]").css('background-color','green');
//         $("#sth tr:eq(" + (index+1) +")").css('background-color','#65e849');
//         // $("#sth tr:eq(" + (index+1) +")").addClass("relevant-color");
//     },
//     'click .remove': function (e, value, row, index) {
//         $("#sth tr:eq(" + (index+1) +")").css('background-color','#e66c6c');
//     }
// };

/*
*
* */
$(function () {
    var validator = new Validator(
        '15',
        {
            max : 100,
            min : 10
        }
    );
    var result = validator.validate_max();
});


function submitVerify() {
    var scan = $("input[name='radioVerify']:checked").val();
    var lineIndex = curRowIndex +1 ;
    if(scan == "Relevant"){
        // $("#sth tr:eq("+lineIndex+")").css('background-color','#65e849');
        $("#sth tr:eq(" + lineIndex +")").removeClass("irrelevant-color");
        $("#sth tr:eq(" + lineIndex +")").addClass("relevant-color");
    }else if(scan == "Irrelevant"){
        // $("#sth tr:eq(" + (index+1) +")").addClass("irrelevant-color");
        // $("#sth tr:eq(" + lineIndex +")").css('background-color','#e66c6c');
        $("#sth tr:eq(" + lineIndex +")").removeClass("relevant-color");
        $("#sth tr:eq(" + lineIndex +")").addClass("irrelevant-color");
    }
    // $('#sth').bootstrapTable('updateRow', {index: 2, row: {"class":"kaixin","score":"0.sdd444","isVerified":"gdfs","effect":"123"}});
    $("#verify").modal("hide");
}

$('#verify').on('hide.bs.modal', function () {
    // alert(curRow.score);
});

// $('#verify').on('show.bs.modal', function (event) {
//     var button = $(event.relatedTarget); // Button that triggered the modal
//     //注意这里的whatever对应前面html代码中button标签下data-whatever属性的后半段
//     var recipient = button.data('whatever') // Extract info from data-* attributes
//     // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
//     // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
//     var modal = $(this);
//     console.log(typeof button);
//     //此处即为修改modal的标题
//     // modal.find('.modal-title').text('New message to ');
//     // modal.find('.modal-body label').innerText("edit");
//     // modal.find('.modal-body input').val(recipient);
// });

function setCookie(cname,cvalue,exdays){
    var d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cname+"="+cvalue+"; "+expires;
}
function getCookie(cname){
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) { return c.substring(name.length,c.length); }
    }
    return "";
}
function checkCookie(){
    var user=getCookie("username");
    if (user!=""){
        alert("欢迎 " + user + " 再次访问");
        $("#nav_login").hide();
        $("#nav_register").hide();
        $("#nav_user").text(user);
        $("#nav_user").css("display","block");
    }
    else {
        // alert("没有cookie");
        // user = prompt("请输入你的名字:","");
        // if (user!="" && user!=null){
        //     setCookie("username",user,30);
        // }
    }
}
function deleteCookies() {
    setCookie("username","",-1);
}