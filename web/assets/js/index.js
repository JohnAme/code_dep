// function verify1() {
//     $(this).css('color','#d22b2b');
//     $('#tr1').css('color','#d22b2b');
// }

// function verify2() {
//     $(this).find('t').css('background-color','red');
// }

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
    {"class":"EditHealth","score":"0.5555555555","isVerified":"2016/10/9 10:15:00","effect":""},
    {"class":"EditHealth","score":"0.2222222222","isVerified":"2016/10/9 10:15:00","effect":""},
    {"class":"EditHealth","score":"0.1111111111","isVerified":"2016/10/9 10:15:00","effect":""},
    {"class":"EditHealth","score":"0.3333333333","isVerified":"2016/10/9 10:15:00","effect":""},
    {"class":"EditHealth","score":"0.6666666666","isVerified":"2016/10/9 10:15:00","effect":""},
    {"class":"EditHealth","score":"0.4444444444","isVerified":"2016/10/9 10:15:00","effect":""}
    ];
/*初始化table数据*/
$(function(){
    // $("#goods").bootstrapTable({
    //     data:json
    // });

    $("#sth").bootstrapTable({
        columns: [{
            field: 'class',
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
            events: window.operateEvents,
            formatter: operateFormatter
        }],
        data: json
    })
});


function operateFormatter(value, row, index) {
    return [
        '<a class="like" href="javascript:void(0)" title="Like">',
        '<i class="glyphicon glyphicon-heart"></i>',
        '</a>  ',
        '<a class="remove" href="javascript:void(0)" title="Remove">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>'
    ].join('')
}


window.operateEvents = {
    'click .like': function (e, value, row, index) {
        // alert('You click like action, row: ' + JSON.stringify(row));
        // $("tr[data-index=sindex]").css('background-color','green');
        $("#sth tr:eq(" + (index+1) +")").css('background-color','#65e849');
    },
    'click .remove': function (e, value, row, index) {
        $("#sth tr:eq(" + (index+1) +")").css('background-color','#e66c6c');
    }
}