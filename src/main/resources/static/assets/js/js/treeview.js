function getTree() {
    var tree = [{
        text: "src",
        nodes: [{
            text: "main",
            nodes: [{
                text: "dao",
                nodes: [{
                    text: "connect",
                },{
                    text: "servlet",
                    nodes: [{
                        text: "loginServlet"
                    },{
                        text: "initTableServlet"
                    }]
                }]
            }]
        }]
    }, {
        text: "web",
        nodes: [{
            text: "assets"
        }, {
            text: "lib"
        }]
    }];

    return tree;
}

$('#tree').treeview({
    data: getTree(),
    backColor: 'white'
});
