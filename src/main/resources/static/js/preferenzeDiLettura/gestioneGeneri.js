
function aggiungiGenere(){
   let tr,td,btn;

   let sel = document.getElementById("select_generi")
   let opt = sel.options[sel.selectedIndex]
   sel.removeChild(opt)

   let table = document.getElementById("utente_generi")
   table.appendChild(tr = document.createElement("tr"))
   tr.className=opt.value
   tr.appendChild(td = document.createElement("td"))

   td.innerHTML = opt.value
   tr.innerHTML+='<td>' +
       '<button type="button" class="btn btn-danger" name="'+opt.value+'" onclick= rimuoviGenere(this)>' +
       '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"\n' +
       '                                     class="bi bi-x-circle" viewBox="0 0 16 16">\n' +
       '                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>\n' +
       '                                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>\n' +
       '                                </svg></button></td>'



}

function rimuoviGenere(button){
   let sel = document.getElementById("select_generi")
   let table = document.getElementById("utente_generi")
   let toDelete= document.getElementsByClassName(button.name)
   table.removeChild(toDelete[0])
  sel.innerHTML+='<option value="'+button.name+'">'+button.name+'</option>'

}

function generi(){

   let table = document.getElementById("utente_generi")
   let col = table.getElementsByTagName("tr")
   let inner;

   //Se non sono stati inseriti generi, inserisco un placeholder
   if(col.length===1){
      inner+='<input type="hidden" name="genere">'
   }

   for(let i=1;i<col.length;i++){
      inner+='<input type="hidden" name="genere" value="'+col[i].className+'">'
   }

   table.innerHTML+=inner

}