"use strict";

// Class definition
let KTProjectList = function () {

    let initChart1 = function () {

        // init chart
        let element = document.getElementById("kt_project_list_chart1");

        if (!element) {
            return;
        }

        let chart = {
            self: null,
            rendered: false
        };

        let initChart_opt = function(){
            let options = {
                series: [],/*44, 55, 13, 43, 22*/
                labels: [],
                chart: {
                    id: 'pieChart',
                    width: 460,
                    height: 350,
                    type: 'pie',
                },
                responsive: [{
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: 'bottom'
                        }
                    }
                }]
            };

            chart.self = new ApexCharts(element, options);
            chart.self.render();
            chart.rendered = true;

            $.getJSON('/mng/main/statistics/company/booth.do', function(response) {
                chart.self.updateOptions(
                        /*series: [10,20,30],
                        labels: ['Active', 'Completed', 'Yet to start']*/
                        response
                )
            })
        }

        initChart_opt();

        // Update chart on theme mode change
        KTThemeMode.on("kt.thememode.change", function() {
            if (chart.rendered) {
                chart.self.destroy();
            }

            initChart_opt();
        });

    }

    let initChart2 = function () {

        // init chart
        let element = document.getElementById("kt_project_list_chart2");

        if (!element) {
            return;
        }

        let chart = {
            self: null,
            rendered: false
        };

        let initChart2_opt = function(){
            let colors = [
                '#267ec3',
                '#26e7a6',
                '#febc3b',
                '#ff6178',
                '#8b75d7',
                '#46b3a9',
                '#d830eb',
                '#de7abd',
                '#338f1f'
            ];

            let options = {
                series: [],
                chart: {
                    toolbar: {
                        show: false
                    },
                    width: 600,
                    height: 350,
                    type: 'bar',
                    events: {
                        click: function(chart, w, e) {
                            // console.log(chart, w, e)
                        }
                    }
                },
                colors: colors,
                plotOptions: {
                    bar: {
                        borderRadius: 3,
                        horizontal: true,
                        columnWidth: '10%',
                        distributed: true,
                    }
                },
                dataLabels: {
                    enabled: false
                },
                legend: {
                    show: false
                },
                xaxis: {
                    categories: [
                        '보트&요트',
                        '무동력보트',
                        '워크보트',
                        '해양부품&장비',
                        '안전&마리나',
                        '해양관광',
                        '해양레저',
                        '수중레저',
                        '서핑'
                    ],
                    labels: {
                        style: {
                            colors: colors,
                            fontSize: '12px'
                        }
                    }
                }
            };

            chart.self = new ApexCharts(element, options);
            chart.self.render();
            chart.rendered = true;

            $.getJSON('/mng/main/statistics/company/fieldPart.do', function(response) {
                chart.self.updateSeries([{
                    data: response
                }])
            })
        }

        initChart2_opt();

        // Update chart on theme mode change
        KTThemeMode.on("kt.thememode.change", function() {
            if (chart.rendered) {
                chart.self.destroy();
            }

            initChart2_opt();
        });
    }

    // Public methods
    return {
        init: function () {
            initChart1();
            initChart2();
        }
    }
}();

// On document ready
KTUtil.onDOMContentLoaded(function() {
    KTProjectList.init();
});

