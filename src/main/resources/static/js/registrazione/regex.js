function validateLettore(){
    var username = document.getElementById ("inputUsername");
    var password = document.getElementById ("inputPassword");
    var nome = document.getElementById ("inputNome");
    var email = document.getElementById ("inputEmail");
    var cognome = document.getElementById ("inputCognome");
    var telefono = document.getElementById ("inputTel");
    var via = document.getElementById ("inputVia");

    //REGEXP
    var rexnome=/^[A-zÀ-ù ‘-]{2,}$/;
    var rexemail=/^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,10}$/;
    var rextel=/^\d+$/;
    var rexvia=/^[0-9A-zÀ-ù ‘-]{2,30}$/;
    //FINE REGEXP
//TEST PER LETTORE//
    if(username.value.length>30){
        username.placeholder = "Username troppo lungo.";
        username.value='';
        username.classList.add('error-placeholder');
        username.focus();
        return false;
    }

    if(!rexnome.test(nome.value)){
        nome.placeholder = "Formato nome errato.";
        nome.value='';
        nome.classList.add('error-placeholder');
        nome.focus();
        return false;
    }

    if(password.length<8){
        password.placeholder = "Password troppo corta.";
        password.value='';
        password.classList.add('error-placeholder');
        password.focus();
        return false;
    }

    if(!rexemail.test(email.value)){
        email.placeholder = "Formato email errato.";
        email.value='';
        email.classList.add('error-placeholder');
        email.focus();
        return false;
    }

    if(!rexnome.test(cognome.value)){
        cognome.placeholder = "Formato cognome errato.";
        cognome.value='';
        cognome.classList.add('error-placeholder');
        cognome.focus();
        return false;
    }

    if(!rextel.test(telefono.value)){
        telefono.placeholder = "Formato telefono errato.";
        telefono.value='';
        telefono.classList.add('error-placeholder');
        telefono.focus();
        return false;
    }

    if(!rexvia.test(via.value)){
        via.placeholder = "Formato Via e Numero Civico errato.";
        via.value='';
        via.classList.add('error-placeholder');
        via.focus();
        return false;
    }

    return true;
}


function validateEsperto(){
    var username = document.getElementById("inputUsername");
    var password = document.getElementById("inputPassword");
    var email = document.getElementById ("inputEmail");
    var nome = document.getElementById("inputNome");
    var cognome = document.getElementById("inputCognome");
    var telefono = document.getElementById("inputTel");
    var via = document.getElementById("inputVia");
    var emailBiblioteca = document.getElementById ("inputLavorain");

    //REGEXP
    var rexnome = /^[A-zÀ-ù ‘-]{2,}$/;
    var rextel = /^\d+$/;
    var rexvia = /^[0-9A-zÀ-ù ‘-]{2,30}$/;
    var rexemail=/^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,10}$/;
    //FINE REGEXP
//TEST PER ESPERTO//
    if(username.value.length>30){
        username.placeholder = "Username troppo lungo.";
        username.value='';
        username.classList.add('error-placeholder');
        username.focus();
        return false;
    }

    if(!rexnome.test(nome.value)){
        nome.placeholder = "Formato nome errato.";
        nome.value='';
        nome.classList.add('error-placeholder');
        nome.focus();
        return false;
    }

    if(password.length<8){
        password.placeholder = "Password troppo corta.";
        password.value='';
        password.classList.add('error-placeholder');
        password.focus();
        return false;
    }

    if(!rexemail.test(email.value)){
        email.placeholder = "Formato email errato.";
        email.value='';
        email.classList.add('error-placeholder');
        email.focus();
        return false;
    }

    if(!rexnome.test(cognome.value)){
        cognome.placeholder = "Formato cognome errato.";
        cognome.value='';
        cognome.classList.add('error-placeholder');
        cognome.focus();
        return false;
    }

    if(!rextel.test(telefono.value)){
        telefono.placeholder = "Formato telefono errato.";
        telefono.value='';
        telefono.classList.add('error-placeholder');
        telefono.focus();
        return false;
    }

    if(!rexvia.test(via.value)){
        via.placeholder = "Formato Via e Numero Civico errato.";
        via.value='';
        via.classList.add('error-placeholder');
        via.focus();
        return false;
    }

    if(!rexemail.test(emailBiblioteca.value)){
        emailBiblioteca.placeholder = "Formato Email Biblioteca errato.";
        emailBiblioteca.value='';
        emailBiblioteca.classList.add('error-placeholder');
        emailBiblioteca.focus();
        return false;
    }
    return true;
}

function validateBiblioteca(){
    var password = document.getElementById("inputPassword");
    var nomeBiblioteca = document.getElementById("inputNomeBiblioteca");
    var telefono = document.getElementById("inputTel");
    var via = document.getElementById("inputVia");
    var emailBiblioteca = document.getElementById ("inputLavorain");

    //REGEXP
    var rexnomeBiblioteca = /^[A-zÀ-ù “‘-]{2,}$/;
    var rextel = /^\d+$/;
    var rexvia = /^[0-9A-zÀ-ù ‘-]{2,30}$/;
    var rexemail=/^[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,10}$/;
    //FINE REGEXP
//TEST PER BIBLIOTECA//
    if(!rexnomeBiblioteca.test(nomeBiblioteca.value)){
        nomeBiblioteca.placeholder = "Formato Nome Biblioteca errato.";
        nomeBiblioteca.value='';
        nomeBiblioteca.classList.add('error-placeholder');
        nomeBiblioteca.focus();
        return false;
    }

    if(password.length<8){
        password.placeholder = "Password troppo corta.";
        password.value='';
        password.classList.add('error-placeholder');
        password.focus();
        return false;
    }

    if(!rextel.test(telefono.value)){
        telefono.placeholder = "Formato telefono errato.";
        telefono.value='';
        telefono.classList.add('error-placeholder');
        telefono.focus();
        return false;
    }

    if(!rexvia.test(via.value)){
        via.placeholder = "Formato Via e Numero Civico errato.";
        via.value='';
        via.classList.add('error-placeholder');
        via.focus();
        return false;
    }

    if(!rexemail.test(emailBiblioteca.value)){
        emailBiblioteca.placeholder = "Formato Email Biblioteca errato.";
        emailBiblioteca.value='';
        emailBiblioteca.classList.add('error-placeholder');
        emailBiblioteca.focus();
        return false;
    }
    return true;
}