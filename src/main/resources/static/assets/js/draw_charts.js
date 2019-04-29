function draw_bar_chart(){

$(function () {
    $('#bar_chart').highcharts({
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: '体重指数'
        },
        subtitle: {
            text: '近一周的数据来源'
        },
        xAxis: [{
            categories: ['10-11', '10-12', '10-13', '10-14', '10-15', '10-16',
                '10-17'],
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}kg',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: '体重/kg',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '消耗卡路里',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            labels: {
                format: '{value} CAL',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: '消耗卡路里',
            type: 'column',
            yAxis: 1,
            color: '#FEDBBF',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6],
            tooltip: {
                valueSuffix: ' CAL'
            }

        }, {
            name: '体重',
            type: 'spline',
            color:'#808080',
            data: [57.0, 56.9, 59.5, 64.5, 68.2, 61.5, 65.2],
            tooltip: {
                valueSuffix: 'kg'
            }
        }]
    });
});
    
}

function draw_pie_chart() {
    $(function () {

        $(document).ready(function () {

            // Build the chart
            $('#pie_chart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'pie'
                },
                title: {
                    text: '近一周消耗卡路里运动源'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                series: [{
                    name: 'Brands',
                    colorByPoint: true,
                    data: [{
                        name: '跑步',
                        color:'#FEDBBF',
                        y: 56.33
                    }, {
                        name: '游泳',
                        color:'#BEE0FE',
                        y: 24.03,
                        sliced: true,
                        selected: true
                    }, {
                        name: '羽毛球',
                        color:'#B3DFBC',
                        y: 10.38
                    }, {
                        name: '走路',
                        color:'#FDA3B6',
                        y: 4.77
                    }, {
                        name: '上楼梯',
                        color:'#808080',
                        y: 0.91
                    }]
                }]
            });
        });
    });

}

function draw_ring_chart() {
    $(function () {

        // Uncomment to style it like Apple Watch
        /*
         if (!Highcharts.theme) {
         Highcharts.setOptions({
         chart: {
         backgroundColor: 'black'
         },
         colors: ['#F62366', '#9DFF02', '#0CCDD6'],
         title: {
         style: {
         color: 'silver'
         }
         },
         tooltip: {
         style: {
         color: 'silver'
         }
         }
         });
         }
         // */

        Highcharts.chart('ring_chart', {

                chart: {
                    type: 'solidgauge',
                    marginTop: 50
                },

                title: {
                    text: '身体数据来源',
                    style: {
                        fontSize: '18px'
                    }
                },

                tooltip: {
                    borderWidth: 0,
                    backgroundColor: 'none',
                    shadow: false,
                    style: {
                        fontSize: '16px'
                    },
                    pointFormat: '{series.name}<br><span style="font-size:2em; color: {point.color}; font-weight: bold">{point.y}%</span>',
                    positioner: function (labelWidth) {
                        return {
                            x: 200 - labelWidth / 2,
                            y: 180
                        };
                    }
                },

                pane: {
                    startAngle: 0,
                    endAngle: 360,
                    background: [{ // Track for Move
                        outerRadius: '112%',
                        innerRadius: '88%',
                        backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[3]).setOpacity(0.1).get(),
                        borderWidth: 0
                    }, { // Track for Exercise
                        outerRadius: '87%',
                        innerRadius: '63%',
                        backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.1).get(),
                        borderWidth: 0
                    }, { // Track for Stand
                        outerRadius: '62%',
                        innerRadius: '38%',
                        backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[2]).setOpacity(0.3).get(),
                        borderWidth: 0
                    }, { // Track for Stand
                        outerRadius: '37%',
                        innerRadius: '13%',
                        backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[1]).setOpacity(0.3).get(),
                        borderWidth: 0
                    }]
                },

                yAxis: {
                    min: 0,
                    max: 100,
                    lineWidth: 0,
                    tickPositions: []
                },

                plotOptions: {
                    solidgauge: {
                        borderWidth: '34px',
                        dataLabels: {
                            enabled: false
                        },
                        linecap: 'round',
                        stickyTracking: false
                    }
                },

                series: [{
                    name: '移动',
                    borderColor: Highcharts.getOptions().colors[0],
                    borderColor: '#FEDBBF',
                    data: [{
                        color: Highcharts.getOptions().colors[0],
                        color: '#FEDBBF',
                        radius: '100%',
                        innerRadius: '100%',
                        y: 80
                    }]
                }, {
                    name: '锻炼',
                    borderColor:'#BEE0FE',
                    data: [{
                        color:'#BEE0FE',
                        radius: '75%',
                        innerRadius: '75%',
                        y: 65
                    }]
                }, {
                    name: '站立',
                    borderColor: '#B3DFBC',
                    data: [{
                        color: '#B3DFBC',
                        radius: '50%',
                        innerRadius: '50%',
                        y: 50
                    }]
                }, {
                    name: '静坐',
                    borderColor:'#808080',
                    data: [{
                        color: '#808080',
                        radius: '25%',
                        innerRadius: '25%',
                        y: 35
                    }]
                }]
            },

            /**
             * In the chart load callback, add icons on top of the circular shapes
             */
            function callback() {

                // Move icon
                // this.renderer.path(['M', -8, 0, 'L', 8, 0, 'M', 0, -8, 'L', 8, 0, 0, 8])
                //     .attr({
                //         'stroke': '#303030',
                //         'stroke-linecap': 'round',
                //         'stroke-linejoin': 'round',
                //         'stroke-width': 2,
                //         'zIndex': 10
                //     })
                //     .translate(190, 26)
                //     .add(this.series[2].group);

                // Exercise icon
                // this.renderer.path(['M', -8, 0, 'L', 8, 0, 'M', 0, -8, 'L', 8, 0, 0, 8, 'M', 8, -8, 'L', 16, 0, 8, 8])
                //     .attr({
                //         'stroke': '#303030',
                //         'stroke-linecap': 'round',
                //         'stroke-linejoin': 'round',
                //         'stroke-width': 2,
                //         'zIndex': 10
                //     })
                //     .translate(190, 61)
                //     .add(this.series[2].group);

                // Stand icon
                // this.renderer.path(['M', 0, 8, 'L', 0, -8, 'M', -8, 0, 'L', 0, -8, 8, 0])
                //     .attr({
                //         'stroke': '#303030',
                //         'stroke-linecap': 'round',
                //         'stroke-linejoin': 'round',
                //         'stroke-width': 2,
                //         'zIndex': 10
                //     })
                //     .translate(190, 96)
                //     .add(this.series[2].group);
            });


    });



}