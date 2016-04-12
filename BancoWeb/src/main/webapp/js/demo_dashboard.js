$(function(){        
    /* reportrange */
    if($("#reportrange").length > 0){   
        $("#reportrange").daterangepicker({                    
            ranges: {
               'Today': [moment(), moment()],
               'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
               'Last 7 Days': [moment().subtract(6, 'days'), moment()],
               'Last 30 Days': [moment().subtract(29, 'days'), moment()],
               'This Month': [moment().startOf('month'), moment().endOf('month')],
               'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            opens: 'left',
            buttonClasses: ['btn btn-default'],
            applyClass: 'btn-small btn-primary',
            cancelClass: 'btn-small',
            format: 'MM.DD.YYYY',
            separator: ' to ',
            startDate: moment().subtract('days', 29),
            endDate: moment()            
          },function(start, end) {
              $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        });
        
        $("#reportrange span").html(moment().subtract('days', 29).format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
    }
    /* end reportrange */
    
    /* Rickshaw dashboard chart */
    // var seriesData = [ [], [] ];
    // var random = new Rickshaw.Fixtures.RandomData(1000);

    // for(var i = 0; i < 100; i++) {
        // random.addData(seriesData);
    // }

    // var rdc = new Rickshaw.Graph( {
            // element: document.getElementById("dashboard-chart"),
            // renderer: 'area',
            // width: $("#dashboard-chart").width(),
            // height: 250,
            // series: [{color: "#33414E",data: seriesData[0],name: 'New'}, 
                     // {color: "#1caf9a",data: seriesData[1],name: 'Returned'}]
    // } );

    // rdc.render();

    // var legend = new Rickshaw.Graph.Legend({graph: rdc, element: document.getElementById('dashboard-legend')});
    // var shelving = new Rickshaw.Graph.Behavior.Series.Toggle({graph: rdc,legend: legend});
    // var order = new Rickshaw.Graph.Behavior.Series.Order({graph: rdc,legend: legend});
    // var highlight = new Rickshaw.Graph.Behavior.Series.Highlight( {graph: rdc,legend: legend} );        

    // var rdc_resize = function() {                
            // rdc.configure({
                    // width: $("#dashboard-area-1").width(),
                    // height: $("#dashboard-area-1").height()
            // });
            // rdc.render();
    // }

    // var hoverDetail = new Rickshaw.Graph.HoverDetail({graph: rdc});

    // window.addEventListener('resize', rdc_resize);        

    // rdc_resize();
    /* END Rickshaw dashboard chart */
    
    /* Donut dashboard chart */
    
    /* END Donut dashboard chart */
	
	
    /* Bar dashboard chart */
    /* END Bar dashboard chart */
    
    /* Line dashboard chart */
    
    /* EMD Line dashboard chart */
    /* Moris Area Chart */
      
    /* End Moris Area Chart */
    /* Vector Map */
    /* END Vector Map */

    
    $(".x-navigation-minimize").on("click",function(){
        setTimeout(function(){
            rdc_resize();
        },200);    
    });
    
    
});

