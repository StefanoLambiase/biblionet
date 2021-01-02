
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
   tr.innerHTML+='<input type="button" value="Rimuovi" name="'+opt.value+'" onclick= rimuoviGenere(this)>'


}

function rimuoviGenere(button){
   let sel = document.getElementById("select_generi")
   let table = document.getElementById("utente_generi")
   let toDelete= document.getElementsByClassName(button.name)
   table.removeChild(toDelete[0])
  sel.innerHTML+='<option value="'+button.name+'">'+button.name+'</option>'

}