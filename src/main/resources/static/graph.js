
var path = d3.path();
path.moveTo(1, 2);
path.lineTo(3, 4);
path.closePath();

const width = 960;
const height = 500;
const colors = d3.scaleOrdinal(d3.schemeCategory10);

const svg = d3.select('#cdc_graph')
    .append('svg')
    .attr('width', width)
    .attr('height', height)
    .attr('pointer-event','all');