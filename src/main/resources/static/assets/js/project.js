$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
});

var recentOperations = [
    {"project":"test_1.0_SNAPSHOT.jar","Operation_time":"2019-04-03 16:03:25","partner":['KJlmfe','Grit'],"Operate":""},
    {"project":"test_2.0_SNAPSHOT.jar","Operation_time":"2019-04-04 16:03:25","partner":"Grit","Operate":""},
    {"project":"test_3.0_SNAPSHOT.jar","Operation_time":"2019-04-05 16:03:25","partner":"Eric","Operate":""},
    {"project":"test_4.0_SNAPSHOT.jar","Operation_time":"2019-04-06 16:03:25","partner":['KJlmfe','Eric','Grit'],"Operate":""},
    {"project":"test_5.0_SNAPSHOT.jar","Operation_time":"2019-04-07 16:03:25","partner":"MinamiKotori","Operate":""},
    {"project":"test_6.0_SNAPSHOT.jar","Operation_time":"2019-04-08 16:03:25","partner":"","Operate":""},
    {"project":"test_7.0_SNAPSHOT.jar","Operation_time":"2019-04-09 16:03:25","partner":['Eric','MinamiKotori'],"Operate":""},
    {"project":"test_8.0_SNAPSHOT.jar","Operation_time":"2019-04-10 16:03:25","partner":"KJlmfe","Operate":""},
    {"project":"test_9.0_SNAPSHOT.jar","Operation_time":"2019-04-11 16:03:25","partner":"Eric","Operate":""}
];

var users = [
    {"name":"KJlmfe","photo":"KJlmfe.jpeg","tag":"Enjoy coding","address":"Nanjing"},
    {"name":"Grit","photo":"Gtit.jpeg","tag":"I love new things.","address":"Shanghai"},
    {"name":"Eric","photo":"Eric.jpeg","tag":"NJU student","address":"Nanjing"},
    {"name":"MinamiKotori","photo":"MinamiKotori.jpeg","tag":"I have nothing to do.","address":"Nanjing"}
];

    //初始化Table
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#my-project').bootstrapTable({
            // url: '/Interface/GetData',         //请求后台的URL（*）
            // method: 'get',                      //请求方式（*）
            data:recentOperations,
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            contentType: "application/x-www-form-urlencoded",
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "no",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [
                {
                    field: 'project',
                    title: '项目名称'
                }, {
                    field: 'Operation_time',
                    title: '操作时间'
                }, {
                    field: 'partner',
                    title: '合作好友',
                    formatter : partner
                }, {
                    field: 'Operate',
                    title: '操作',
                    events: window.buttonEvents,
                    formatter: operateButtons //自定义方法，添加操作按钮
                },
            ],
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset:params.offset
        };
        return temp;
    };
    return oTableInit;
};

function operateButtons(value, row, index) {//赋予的参数
    return [
        '<div class="dropdown"><a class="btn add dropdown-toggle" data-toggle="dropdown">添加</a>' +
        '<ul class="dropdown-menu">\n' +
        '<li><a class="user" href="#" onclick="addPartner($(this).text())">'+users[0].name+'</a></li>\n' +
        '<li><a class="user" href="#" onclick="addPartner($(this).text())">'+users[1].name+'</a></li>\n' +
        '<li><a class="user" href="#" onclick="addPartner($(this).text())">'+users[2].name+'</a></li>\n' +
        '<li><a class="user" href="#" onclick="addPartner($(this).text())">'+users[3].name+'</a></li>\n' +
        '</ul></div>',
        '<a class="btn">查看依赖</a>',
        '<a class="btn btn-danger">删除</a>'
    ].join('');
}

var curOp = "";

function addPartner(username) {
    curOp = username;
}

window.buttonEvents = {
    'click .like': function (e, value, row, index) {
        alert(row.project);
    },
    'click .remove': function (e, value, row, index) {

    },
    'click .add': function (e, value, row, index) {
        // var curPartner = row.partner;
        // console.log(curPartner);
    },
    'click .user': function (e, value, row, index) {
        var curPartner = row.partner;
        if((typeof curPartner) == 'string'){
            if(curPartner == curOp){
                alert("该好友已存在");
            }else{
                var time = row.Operation_time;
                var arr = recentOperations.filter(function(p){
                    return p.Operation_time === time;
                });
                arr[0].partner = "sdsd";
                // arr[0].partner.splice(0,0,curPartner);
            }
        }
    }
};

// 填充表格的合作好友一列
function partner(value,row,index) {
    if(recentOperations[index].partner.length==0){
        return;
    }
    var first = true;
    if((typeof recentOperations[index].partner) == 'string'){
        return [
            '<a class="t-partner" data-toggle="popover" title=' + recentOperations[index].partner + '>' + recentOperations[index].partner + '</a>',
        ].join('');
    }else {
        var result = '';
        var arr = recentOperations[index].partner;
        for (var i=0;i<arr.length;i++){
            if(first == false){
                result = result + ' | ';
            }
            first = false;
            result = result + '<a class="t-partner" data-toggle="popover" data-title=' + recentOperations[index].partner[i] + '>' + recentOperations[index].partner[i] + '</a>';
        }
        return [result].join('');
    }
}

// $(function () {
//     $("[data-toggle='popover']").popover({
//         trigger : 'hover',
//         placement : 'top',
//         html : true,
//         content : ContentMethod($(".popover:hover").html())
//     });
// });

$(function () {
    $('[data-toggle="popover"]').each(function () {
        var element = $(this);
        var name = element.html();
        element.popover({
            trigger: 'hover',
            placement: 'top',
            title: name,
            html: 'true',
            content: ContentMethod(name),
        });
    });
});

function ContentMethod(name) {
    var curUser = users.filter(function(p){
        return p.name === name;
    });
    curUser = curUser[0];
    return '<div class="d-table-cell col-md-3">\n' +
        '<img class="icon" src="assets/picture/' + name + '.jpeg" alt="">\n' +
        '</div>\n' +
        '<div class="col-md-9">\n' +
        '<div class="friends-name">' + name + '</div>\n' +
        '<div class="friends-tag">' + curUser.tag + '</div>\n' +
        '<img class="address-icon" src="assets/picture/address-icon.png" alt="">\n' +
        '<div class="friends-address">'+ curUser.address +'</div>\n' +
        '</div>';
}