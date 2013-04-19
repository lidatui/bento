
$(document).ready(function(){

    $('header h1').on('click', function(){
        $('#main iframe').prop('src', 'main.html');
    });

    $('header a').on('click', function(e){
        e.preventDefault();

        var $this = $(this);
        $('#main iframe').prop('src', $this.prop('href'));
    });
});
