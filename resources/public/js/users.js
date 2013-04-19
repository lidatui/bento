
var clearUserForm = function (){
    $('#txtName').parent().parent().removeClass('error');
    $('#txtName').next().hide();
    $('#txtName').val('');
    $('#txtRtx').val('');
    $('#txtIp').parent().parent().removeClass('error');
    $('#txtIp').next().hide();
    $('#txtIp').val('');
    $('#user-modal .modal-body .alert-error').hide();
    $('#user-modal').removeData('item');
}

$(document).ready(function(){

    $('#user-add').on('click',function(){
        clearUserForm();
        $('#user-modal').modal('show');
    });

    $('#user-edit').on('click',function(){
        var tr = $('#user-body tr.warning')[0];
        if(!tr){
            alert('请选择一行进行编辑');
            return ;
        }
        var item = $.data(tr,'item');
        $('#txtName').val(item.name);
        $('#txtRtx').val(item.rtx);
        $('#txtIp').val(item.ip);

        $('#user-modal').data('item',item);
        $('#user-modal').modal('show');
    });

    $('#user-save').on('click',function(){
        var params = {};
        params = $.extend(params, $('#user-modal').data('item'));
        params.name = $('#txtName').val();
        params.rtx = $('#txtRtx').val();
        params.ip = $('#txtIp').val();
        if(!params.name){
            $('#txtName').parent().parent().addClass('error');
            $('#txtName').next().show();
            return;
        }
        if(!params.ip){
            $('#txtIp').parent().parent().addClass('error');
            $('#txtIp').next().show();
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'user',
            data: params,
            dataType: 'text',
            cache: false
        }).done(function(result){
            if(params.id){
                loadUsers();
            }else{
                loadUsers(0);
            }

            $('#user-modal').modal('hide');
            clearUserForm();
        }).fail(function(data){
            $('#user-modal .modal-body .alert-error').show().text(data.statusText + ': ' + data.responseText);
        });
    });

    $('#user-remove').on('click',function(){
        var tr = $('#user-body tr.warning')[0];
        if(!tr){
            alert('请选择一行进行删除');
            return ;
        }
        var item = $.data(tr,'item');
        if(!confirm('你确定要删除吗?')){
            return;
        }
        $.ajax({
            type: 'DELETE',
            url: 'user',
            data: {
                id: item.id
            },
            dataType: 'text',
            cache: false
        }).done(function(result){
            loadUsers();
        }).fail(function(data){
            console.log(data.statusText + ': ' + data.responseText);
        });
    });

    $('#user-body').on('click', 'tr', function(){
        $('#user-body tr').removeClass('warning');
        $(this).addClass('warning');
    });

    loadUsers(0,12);
});

var loadUsers = function (pageNo, pageSize){

    if(pageNo >= 0){
        $('#userPaginator').data('pageNo',pageNo);
    }else{
        pageNo = $('#userPaginator').data('pageNo');
        pageNo = pageNo >=0 ? pageNo : 0;
    }
    if(pageSize >= 1){
        $('#userPaginator').data('pageSize',pageSize);
    }else{
        pageSize = $('#userPaginator').data('pageSize');
        pageSize = pageSize >=1 ? pageSize : 10;
    }

    $.ajax({
        url: 'users',
        dataType: 'json',
        data: {
            page: pageNo,
            limit: pageSize
        },
        cache: false
    }).done(function(result){
        renderPaginator('userPaginator', pageNo,result.totalCount,pageSize,loadUsers);

        var trHtmls = [];
        $.each(result.items, function(index,item){
            trHtmls.push('<tr>');
            trHtmls.push('<td style="width: 50px;text-align: center;">');
            trHtmls.push(pageNo * pageSize +index+1);
            trHtmls.push('</td>');
            trHtmls.push('<td style="text-align: center;">');
            trHtmls.push(item.name);
            trHtmls.push('</td>');
            trHtmls.push('<td style="text-align: center;">');
            trHtmls.push(item.rtx);
            trHtmls.push('</td>');
            trHtmls.push('<td style="text-align: center;">');
            trHtmls.push(item.ip);
            trHtmls.push('</td>');
            trHtmls.push('</tr>');
        });

        $('#user-body').empty().html(trHtmls.join(''));

        $.each($('#user-body tr'), function(index, tr){
            $.data(tr,'item', result.items[index]);
        });
    });
}

var renderPaginator = function (ulId,pageNo, totalCount, pageSize ,load){
    var $ul = $('#'+ulId).empty();
    var totalPage = totalCount % pageSize === 0 ? parseInt(totalCount/pageSize) : parseInt(totalCount/pageSize) + 1;
    totalPage = totalPage ? totalPage : 0;
    if(totalPage === 0){
        pageNo = 0;
    }else if(pageNo > totalPage){
        pageNo = totalPage;
    }else if(pageNo < 1 && totalPage != 0){
        pageNo = 0;
    }
    //
    var $prev = $('<li><a>«</a></li>');
    if(pageNo<=0){
        $prev.addClass('disabled');
    }else{
        $prev.find('a').on('click', function(e){
            e.preventDefault();
            load(pageNo-1,pageSize)
        });
    }
    $ul.append($prev);
    /////
    var list = [1];
    if(pageNo+1 > 4 ){
        list.push('...');
    }
    for(var i= 0; i < 5; i++){
        var no = pageNo - 1 + i;
        if(no > 1 && no <= totalPage-1){
            list.push(no);
        }
    }
    if(pageNo+2 < totalPage-1){
        list.push('...');
    }
    if(totalPage>1){
        list.push(totalPage);
    }
    $.each(list, function(index, item){
        var $li = $('<li><a></a></li>');
        if(item === '...'){
            $li.find('a').text('...');
        }else if(item === pageNo+1){
            $li.addClass('active').find('a').text(item);
        }else{
            $li.find('a').text(item).prop('title','第'+item+'页').on('click', function(e){
                e.preventDefault();
                load(item-1,pageSize);
            });
        }
        $ul.append($li);
    });
    //
    var $next = $('<li><a title="下一页">»</a></li>');
    if(pageNo+1>=totalPage){
        $next.addClass('disabled');
    }else{
        $next.find('a').on('click', function(e){
            e.preventDefault();
            load(pageNo+1,pageSize);
        });
    }
    $ul.append($next);
}