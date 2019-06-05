// function check() {
//     var file=($("#file"))[0].files[0];
//     var formdata=false;
//     if(window.FormData){
//         formdata=new FormData;
//     }
//     formdata.append("file",file);
//
//     $.ajax({
//             url: "/trace/upload",
//             type: "POST",
//             data: formdata,
//             cache: false,
//             contentType: false,
//             processData: false,
//             success: function(data){
//                 alert(data);
//             }
//     });
// }

function getCDCGraph(){
    var pname="iTrust";
    var cname="ResetPasswordAction";
    var dc="0.7";
    var cd="0.9";
    var url="/trace/CDCGraph?project=";
    url=url.concat(pname);
    url=url.concat("&cname=")
    url=url.concat(cname);
    url=url.concat("&dc=");
    url=url.concat(dc);
    url=url.concat("&cd=");
    url=url.concat(cd);

    window.location.replace(url);
}

$(function(){
    function showCandidateList(pid) {
        $.get("/trace/getCandidateList?pid="+encodeURIComponent(pid),
            function (data) {
                $("#sth").bootstrapTable({
                    columns: [{
                        field: '_class',
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
                        events: window.operateEvents,
                        formatter: operateFormatter
                    }],
                    data: data,
                    onDblClickRow: function (row, $element, field) {
                        var modal = $("#verify");
                        modal.modal();
                        modal.find('.modal-body label').innerText = row.class;
                    }
                });
            },"json");

        return true;
    }

    function showProjectDir(pid,pname){
        $.get("/trace/getProjectDir?pid="+encodeURIComponent(pid)+"&pname="+encodeURIComponent(pname),
            function (data) {
                $("#tree").treeview({
                    data: [data]
                });
            },"json");
    }

    function showUc(){//todo
        $.get("/trace/uc",
            function(data){
                $("p#box").css("white-space","pre-wrap");
                $("p#box").text(data);
            });
    }

    // showUc();
    // showProjectDir(6,"movies-java-spring-data-neo4j-master");
    // showCandidateList(6);
})