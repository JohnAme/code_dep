var json = [
    {"class":"FileController","score":"0.150265486","isVerified":"","effect":""},
    {"class":"ManageService","score":"0.211244230","isVerified":"","effect":""},
    {"class":"LoginServlet","score":"0.588179880","isVerified":"","effect":""},
    {"class":"EditHealth","score":"0.648066566","isVerified":"","effect":""},
    {"class":"StorageService","score":"0.314593012","isVerified":"","effect":""},
    {"class":"TempUcUtil","score":"0.477558188","isVerified":"","effect":""},
    {"class":"StartController","score":"0.478028838","isVerified":"","effect":""},
    {"class":"Application","score":"0.681305107","isVerified":"","effect":""},
    {"class":"UserController","score":"0.192317414","isVerified":"","effect":""},
    {"class":"StyleService","score":"0.363371313","isVerified":"","effect":""},
    {"class":"RegisterServlet","score":"0.152228796","isVerified":"","effect":""},
    {"class":"ProjectHealth","score":"0.602343033","isVerified":"","effect":""},
    {"class":"graphService","score":"0.117572972","isVerified":"","effect":""},
    {"class":"TempUcUtil","score":"0.456432497","isVerified":"","effect":""},
    {"class":"StartController","score":"0.162250677","isVerified":"","effect":""},
    {"class":"Application","score":"0.366349035","isVerified":"","effect":""},
    {"class":"FileController","score":"0.370574273","isVerified":"","effect":""},
    {"class":"ManageService","score":"0.521773796","isVerified":"","effect":""},
    {"class":"LoginServlet","score":"0.587244266","isVerified":"","effect":""},
    {"class":"EditHealth","score":"0.628434164","isVerified":"","effect":""},
    {"class":"StorageService","score":"0.180451279","isVerified":"","effect":""},
    {"class":"TempUcUtil","score":"0.561588318","isVerified":"","effect":""},
    {"class":"StartController","score":"0.601264803","isVerified":"","effect":""},
    {"class":"Application","score":"0.447336157","isVerified":"","effect":""},
    {"class":"FileController","score":"0.280746343","isVerified":"","effect":""},
    {"class":"ManageService","score":"0.471788241","isVerified":"","effect":""},
    {"class":"LoginServlet","score":"0.297209711","isVerified":"","effect":""},
    {"class":"EditHealth","score":"0.205576479","isVerified":"","effect":""},
    {"class":"StorageService","score":"0.510219166","isVerified":"","effect":""},
    {"class":"TempUcUtil","score":"0.417502026","isVerified":"","effect":""},
    {"class":"StartController","score":"0.221008563","isVerified":"","effect":""},
    {"class":"Application","score":"0.232824081","isVerified":"","effect":""},
];

/*初始化table数据*/
$("#table2").bootstrapTable({
    columns: [{
        field: "class",
        title: 'class'
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
    url: "initTableServlet",
    method: "get",
    dataType : "json",
    data: json,
    sortable: true,
    sortOrder: "desc",
    // sortName: 'isVerified',
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

//指定图标的配置和数据
var option = {
    title : {
        text: '好友选择',
        x:'center',
        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
            fontFamily: 'Arial, Verdana, sans...',
            fontSize: 8,
            fontStyle: 'normal',
            fontWeight: 'normal',
        },
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        x: '25%',
                        width: '100%',
                        funnelAlign: 'left',
                        max: 1548
                    }
                }
            },
        }
    },
    series : [
        {
            name:'好友选择',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:3, name:'Relevant'},
                {value:1, name:'Irrelevant'},
                {value:1, name:'Skip'},
                {value:1, name:'Stop'},
            ]
        }
    ]
};


$('#verify').on('shown.bs.modal',function(){
    //初始化echarts实例
    var myChart = echarts.init(document.getElementById('chartmain'));
//使用制定的配置项和数据显示图表
    myChart.setOption(option);
    myChart.resize();
});

function submitVerify() {
    var scan = $("input[name='radioVerify']:checked").val();
    var lineIndex = curRowIndex +1 ;
    var params;
    if(scan == "Relevant" && curRow.isVerified!="Relevant"){
        // $("#sth tr:eq("+lineIndex+")").css('background-color','#65e849');
        $("#sth tr:eq(" + lineIndex +")").removeClass("irrelevant-color");
        $("#sth tr:eq(" + lineIndex +")").addClass("relevant-color");
        params = {
            "class":$("#verifyClass").text().split(":")[1].slice(1),
            "isVerified":"Relevant"
        };
        $.ajax({
            "url" : "updateTableServlet",
            "dataType": "json",
            "data" : params,
            "type" : "post",
            "success" : function(data) {
                alert(data.message);
                $("#sth").bootstrapTable('refresh');
                $("#table2").bootstrapTable('refresh');
            },
            "error" : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("更新table出错");
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }else if(scan == "Irrelevant" && curRow.isVerified!="Irrelevant"){
        $("#sth tr:eq(" + lineIndex +")").removeClass("relevant-color");
        $("#sth tr:eq(" + lineIndex +")").addClass("irrelevant-color");
        params = {
            "class":$("#verifyClass").text().split(":")[1].slice(1),
            "isVerified":"Irrelevant"
        };
        $.ajax({
            "url" : "updateTableServlet",
            "dataType": "json",
            "data" : params,
            "type" : "post",
            "success" : function(data) {
                alert(data.message);
                $("#sth").bootstrapTable('refresh');
                $("#table2").bootstrapTable('refresh');
            },
            "error" : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("更新table出错");
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }
    // $('#sth').bootstrapTable('updateRow', {index: 2, row: {"class":"kaixin","score":"0.sdd444","isVerified":"gdfs","effect":"123"}});
    $("#verify").modal("hide");
}

function submitVerify2() {
    var scan = $("input[name='radioVerify']:checked").val();
    var lineIndex = curRowIndex +1 ;
    var params;
    if(scan == "Relevant" && curRow.isVerified!="Relevant"){
        // $("#sth tr:eq("+lineIndex+")").css('background-color','#65e849');
        $("#sth tr:eq(" + lineIndex +")").removeClass("irrelevant-color");
        $("#sth tr:eq(" + lineIndex +")").addClass("relevant-color");
        params = {
            "class":$("#verifyClass2").text().split(":")[1].slice(1),
            "isVerified":"Relevant"
        };
        $.ajax({
            "url" : "updateTable2Servlet",
            "dataType": "json",
            "data" : params,
            "type" : "post",
            "success" : function(data) {
                alert(data.message);
                $("#sth").bootstrapTable('refresh');
                $("#table2").bootstrapTable('refresh');
            },
            "error" : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("更新table出错");
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }else if(scan == "Irrelevant" && curRow.isVerified!="Irrelevant"){
        $("#sth tr:eq(" + lineIndex +")").removeClass("relevant-color");
        $("#sth tr:eq(" + lineIndex +")").addClass("irrelevant-color");
        params = {
            "class":$("#verifyClass2").text().split(":")[1].slice(1),
            "isVerified":"Irrelevant"
        };
        $.ajax({
            "url" : "updateTable2Servlet",
            "dataType": "json",
            "data" : params,
            "type" : "post",
            "success" : function(data) {
                alert(data.message);
                $("#sth").bootstrapTable('refresh');
                $("#table2").bootstrapTable('refresh');
            },
            "error" : function(XMLHttpRequest, textStatus, errorThrown) {
                alert("更新table出错");
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }
    // $('#sth').bootstrapTable('updateRow', {index: 2, row: {"class":"kaixin","score":"0.sdd444","isVerified":"gdfs","effect":"123"}});
    $("#verify").modal("hide");
}

$('#verify').on('hide.bs.modal', function () {
    // alert(curRow.score);
});