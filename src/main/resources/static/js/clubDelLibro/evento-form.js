var libri = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('titolo'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    remote: {
      url: '/prenotazione-libri/find-libri-by-titolo-contains?q=%QUERY',
      wildcard: '%QUERY'
    }
  });
  
$('.typeahead').typeahead(null, {
    name: 'libro',
    displayKey: 'titolo',
    source: libri
}).addClass("form-control");

$('#_libro').on(
    "typeahead:selected typeahead:autocompleted", 
    function(e,datum) { 
        $("#libro").val(datum.idLibro || ""); 
    }
)