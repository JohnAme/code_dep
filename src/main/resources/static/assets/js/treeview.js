function getTree() {
    var tree = [{
        text: "node"
    }, {
        text: "src",
        nodes: [{
            text: "main",
            nodes: [{
                text: "java",
                nodes: [{
                    text: "com",
                    nodes: [{
                        text: "dao",
                        nodes:[{
                            text:"DBSqlSession",

                        }]
                    }, {
                        text: "model"
                    }]
                }]
            }, {
                text: "resources"
            }]
        }, {
            text: "node2"
        }]
    }, {
        text: "node3"
    }, {
        text: "node4"
    }, {
        text: "node5",
        nodes: [{
            text: "node6"
        }, {
            text: "node7"
        }]
    }];

    return tree;
}
/*
$('#tree').treeview({
    data: getTree(),
    backColor: 'white'
});
*/
