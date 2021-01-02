
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
   tr.innerHTML+='<td><input type="button" value="Rimuovi" name="'+opt.value+'" onclick= rimuoviGenere(this)></td>'


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