
$(function() {

    $('#recommenderLink').click(function () {
        $('#addItemDiv').hide();
        $('#recommenderDiv').show();
        $('#addUserDiv').hide();
        $('#addRatingDiv').hide();
    });

    $('#itemLink').click(function () {
        $('#recommenderDiv').hide();
        $('#addItemDiv').show();
        $('#addUserDiv').hide();
        $('#addRatingDiv').hide();
    });

    $('#userLink').click(function () {
        $('#addItemDiv').hide();
        $('#recommenderDiv').hide();
        $('#addUserDiv').show();
        $('#addRatingDiv').hide();
    });

    $('#ratingLink').click(function () {
        $('#addItemDiv').hide();
        $('#recommenderDiv').hide();
        $('#addUserDiv').hide();
        $('#addRatingDiv').show();
    });

    $('#recommenderSubmit').click(function(event) {

        event.preventDefault();

        var idUserVal = $('#idUserQuery').val();

        if(idUserVal !== "") {

            $.get('api/recommendation', 
                {
                    idUser: idUserVal
                },
                function(data) {

                    var tableRows = '';

                    for(var d in data) {
                        var desc = data[d];
                        var result = '<tr>' +
                            '<th scope="row">' + desc.id + '</th>' +
                            '<th>' + desc.title + '</th>' +
                            '<th>' + desc.geners + '</th>' +
                            '<th>' + desc.rating + '</th>' +
                            '</tr>';

                        tableRows = tableRows + result;
                    }

                    $('#recommendationResultData').empty();
                    $('#recommendationResultData').html(tableRows);

                    $('#recommendationsDiv').show();
                })
            .fail(function() {
                alert("error");
            });
        }
    });

    $('#addItemSubmit').click(function(event) {

        event.preventDefault();

        var isbnVal = $('#isbnItem').val();
        var titleVal = $('#titleItem').val();
        var authorVal = $('#authorItem').val();
        var yearOfPublicationVal = $('#yearOfPublicationItem').val();

        if(isbnVal !== "" && 
            titleVal !== "" && 
            authorVal !== "" && 
            yearOfPublicationVal != "") { 

            $.post('api/addItem',
                {
                    isbn: isbnVal,
                    title: titleVal,
                    author: authorVal, 
                    yearOfPublication: yearOfPublicationVal
                },
                function() {
                    alert("Item saved correctly");
                });

        }

    })

});