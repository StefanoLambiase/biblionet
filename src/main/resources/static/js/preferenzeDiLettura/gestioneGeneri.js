
function aggiungiGenere(){
   let tr,td,btn;

   let sel = document.getElementById("select_generi")
   let opt = sel.options[sel.selectedIndex]
   sel.removeChild(opt)

   let table = document.getElementById("utente_generi")
   table.appendChild(tr = document.createElement("tr"))
   tr.appendChild(td = document.createElement("td"))
   td.innerHTML = opt.value
   btn = document.createElement("input")
   btn.type = "button"
   btn.value = "Rimuovi"
   btn.addEventListener("click", aggiungiGenere)
   tr.appendChild(btn)
   

}

function rimuoviGenere(){

}