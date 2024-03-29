
var loadOrderCount = function(){
    var planId = $.data($('body')[0], 'planId');
    $.ajax({
        type: 'GET',
        url: 'order/count',
        data: {planId: planId},
        dataType: 'json',
        cache: false
    }).done(function(data){
        if(data.orderCount){
            $('#orderCount').text(data.orderCount);
        }else{
            $('#orderCount').text(0);
        }
    }).fail(function(data){

    });
}


var loadOrderMonthCount = function(){
    $.ajax({
        type: 'GET',
        url: 'order/monthCount',
        data: {},
        dataType: 'json',
        cache: false
    }).done(function(data){
        if(data.orderMonthCount){
            $('#orderMonthCount').text(data.orderMonthCount);
        }else{
            $('#orderMonthCount').text(0);
        }
    }).fail(function(data){

    });
}

$(document).ready(function(){

    $("#orderUser").select2({
        width: '100%'
    });

    $('#order-add').on('click', function(){
        $('#orderUser').parent().parent().removeClass('error');
        $('#orderUser').next().hide();
        $('#order-modal').modal('show');
    });

    $('#order-save').on('click', function(){
        var selected = $('#orderUser').select2('val');
        console.log(selected);

        if(selected.length === 0){
            $('#orderUser').parent().parent().addClass('error');
            $('#orderUser').next().show();
            return;
        }

        var params = {
            userIds: selected,
            planId: 1,
            createUserId: 1
        };

        $.ajax({
            type: 'POST',
            url: 'order',
            data: params,
            dataType: 'text',
            cache: false
        }).done(function(result){
            $('#order-modal').modal('hide');
            $('#orderUser').select2('val', '');
            $('#orderUser').parent().parent().removeClass('error');
            loadOrderCount();
            loadOrderMonthCount();
        }).fail(function(data){
            $('#order-modal .modal-body .alert-error').show().text(data.statusText + ': ' + data.responseText);
        });
    });



    $.ajax({
        type: 'GET',
        url: 'plan/now',
        data: {},
        dataType: 'json',
        cache: false
    }).done(function(plan){
        if(plan.id){
            $('#order-add').text('我要预订晚餐！').prop('disabled','');
            $.data($('body')[0],'planId',plan.id);
            loadOrderCount();
        }else{
            $('#order-add').text('别着急，预订还没开始').prop('disabled','disabled');
        }
    }).fail(function(data){

    });


    loadOrderMonthCount();


    $('#chart-line').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },

        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
});
