

document.addEventListener("DOMContentLoaded", function(){
    
    const contacto = document.getElementsByClassName("contactos");
    const listaContactos= document.getElementById("contenedor_chat");

    var StompClient = null;
    conectarWebosocket();

    for (let c of contacto) {
        c.addEventListener("click",function(){
            listaContactos.style.display="none";
            listaContactos.style.zIndex="0";
            console.log("si hay contacto")
            const contacto = c.querySelector(".nombreContacto").innerText;

            crearChat(contacto);
            console.log(contacto)
            mensajes(contacto)

        })
    }

    agregarContacto();
 
    
    
    function conectarWebosocket(){

        var socket = new SockJS("/chatMensajes");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            console.log("Conectado: " + frame);

            // Suscribirse a la cola privada
            stompClient.subscribe('/user/queue/messages', function(message) {
                var msg = message.body;
                console.log("Mensaje privado recibido:", msg);

                mostrarMensaje(msg,"receptor")
            });
        });


    
    

    }


    function mensajes (contacto){
        fetch ("http://localhost:8085/cargarMensajes?contacto="+contacto)
        .then (respuesta => respuesta.json())
        .then(json => {
            for (let c of json){

                if (contacto != c.id_emisor){
                    mostrarMensaje(c.mensaje,"remitente")    
                }
                else {
                    mostrarMensaje(c.mensaje,"receptor")
                }

                
            }
        })
        .catch (error => console.error(error));
    }

    function crearChat(nombre){

        const contenedorPadre = document.getElementById("contenedor_padre");
        /* Aquí está la creación de los elementos */
        let contendorChat = document.createElement("div");
        let nombreContacto= document.createElement("p");
        let contenedorMensajes = document.createElement("div")
        let contenedorInputChat = document.createElement("div");
        let contenedorEncabezadoChat= document.createElement("div");
        let contenedorBackIcon= document.createElement("div");

        /* Asignación de Id*/
        contenedorEncabezadoChat.id="contenedorEncabezadoChat";
        contendorChat.id="contenedorChat";
        nombreContacto.id="nombreContacto";
        contenedorMensajes.id="contenedorMensajes"
        contenedorInputChat.id ="contenedorInputChat";
        contenedorBackIcon.id ="contenedorBackIcon";
        contendorChat.appendChild(contenedorBackIcon);
        contendorChat.appendChild(contenedorEncabezadoChat);
        contenedorPadre.appendChild(contendorChat);
        contendorChat.appendChild(nombreContacto);
        contendorChat.appendChild(contenedorMensajes);
        contendorChat.appendChild(contenedorInputChat);
        
        

        nombreContacto.innerHTML = nombre;

        contenedorEncabezadoChat.appendChild(nombreContacto);

        /*Aquí están las funciones style*/
        contenedorChat(contendorChat)
        contenedorMensaje(contenedorMensajes)
        contenedorInputChatStyle(contenedorInputChat)
        contendorBackIconCreate(contenedorBackIcon);
        

        
    }


})




function contenedorChat(contenedor){

    Object.assign(contenedor.style,{
        zIndex:"1",
        width:"60%",
        height:"80%",
        backdropFilter:"blur(10px)",
        display:"flex",
        flexDirection:"column",
        alignItems:"center",
        borderRadius:"30px"
    })
}

function contenedorInputChatStyle(objeto){
    Object.assign(objeto.style,{
        width:"100%",
        height:"10%",
        display: "flex",
        justifyContent:"center",
        alignItems:"center"
    })

    const input = document.createElement("input");
    const boton = document.createElement("button");

    input.id = "inputChat";
    boton.id = "botonChat";

    boton.innerHTML="Send"

    Object.assign(input.style,{
        width: "80%",
        height: "100%",
        borderRadius:"20px",
        backdropFilter:"blur(10px)"

    })
    objeto.appendChild(input);
    objeto.appendChild(boton);


    enviarMensaje(input);

}


function contenedorMensaje(objeto){

    Object.assign(objeto.style,{
        width: "100%",
        height:"70%",
        display: "flex",
        flexDirection:"column"
    })
}


function contendorBackIconCreate(objeto){

    const svg = `
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024" id="back">
            <path d="m548.3 922-44.5-44.5-106.2-106.2-129-129-110.9-110.9c-18-18-35.7-36.2-54-54l-.8-.8v70.7l44.5-44.5 106.2-106.2 129-129 110.9-110.9c18-18 36.2-35.8 54-54l.8-.8c9.1-9.1 14.6-22.5 14.6-35.4 0-12.3-5.4-26.9-14.6-35.4-9.5-8.7-22-15.2-35.4-14.6-13.3.6-25.8 5.1-35.4 14.6L433 75.6 326.8 181.8l-129 129L86.9 421.7c-18 18-36.2 35.8-54 54l-.8.8c-19.1 19.1-19.1 51.6 0 70.7 14.9 15 29.8 29.8 44.6 44.7l106.2 106.2 129 129L422.8 938c18 18 35.8 36.2 54 54l.8.8c9.1 9.1 22.5 14.6 35.4 14.6 12.3 0 26.9-5.4 35.4-14.6 8.7-9.5 15.2-22 14.6-35.4-.7-13.3-5.1-25.8-14.7-35.4z"></path>
            <path d="M67.3 562h853.2c11.5 0 23.1.1 34.6 0h1.5c12.8 0 26.3-5.6 35.4-14.6 8.7-8.7 15.2-22.9 14.6-35.4-.6-12.9-4.8-26.3-14.6-35.4-9.8-9-21.8-14.6-35.4-14.6H103.4c-11.5 0-23.1-.1-34.6 0h-1.5c-12.8 0-26.3 5.6-35.4 14.6-8.7 8.7-15.2 22.9-14.6 35.4.6 12.9 4.8 26.3 14.6 35.4 9.9 8.9 21.9 14.6 35.4 14.6z"></path>
        </svg>
    `;
    objeto.insertAdjacentHTML("beforeend", svg);

    document.getElementById("back").addEventListener("click",function(){
        back();    
    })

    
    

}

function back(){
    const contenedorPadre= document.getElementById("contenedorChat");
    contenedorPadre.remove();

    const lista = document.getElementById("contenedor_chat");
    lista.style.display= "flex";
    lista.style.zIndex="1";
}



function enviarMensaje(objeto){

    objeto.addEventListener("keydown", function(event){
        if (event.key === "Enter"){
            mensaje= objeto.value; 
            if (mensaje != ""){

                const destinatario = document.getElementById("nombreContacto").innerText;
                stompClient.send("/app/mensajes", {}, JSON.stringify({
                    
                    destinatario: destinatario,
                    mensaje: mensaje,
                    hora: Date()
                }));
                objeto.value = ""; 
                mostrarMensaje(mensaje,"remitente")
            }
        }
    })

}

/* Esto muestra el mensaje, si la pc se empieza a sobrecalentar apaga rápidamente */
function mostrarMensaje(texto, usuario){

    padre = document.getElementById("contenedorMensajes");
    contenedorDeMensaje = document.createElement("div");
    contenedorDeMensaje.className = "contenedorMensaje" ;
    mensaje = document.createElement("p");
    mensaje.className= "mensaje"
    padre.appendChild(contenedorDeMensaje);
    contenedorDeMensaje.appendChild(mensaje);
    mensaje.innerText = texto;
    if (usuario === "remitente"){
        
        Object.assign(mensaje.style,{
            textAlign:"end",
            marginRight: "20px"
        })
    }
    else {
        Object.assign(mensaje.style,{
            textAlign: "start", 
            marginLeft: "20px"
        })
    }

}
function agregarContacto(){
    let contenedorChat = document.getElementById("contenedor_chat");
    let boton = document.getElementById("registrarContacto");
    boton.addEventListener("click", function(){
        contenedorChat.style.display= "none";

        CrearFormularioAgregarContacto()

    })
}


function CrearFormularioAgregarContacto(){
    //crear objetos
   let contenedorFormulario =document.createElement("div");
   let contenedorPadre = document.getElementById("contenedor_padre")
   let formulario = document.createElement("form");
   let input = document.createElement("input");
   let boton = document.createElement("button");

    // agregar id
   formulario.id = "formularioContacto"
   boton.id="botonFormlarioContacto"
   input.id = "inputFormularioContacto"
   input.name="id_contacto"
   contenedorFormulario.id = "contenedorFormulario";

   //agregar hijo
   contenedorPadre.appendChild(contenedorFormulario);
    contenedorFormulario.appendChild(formulario);
    formulario.appendChild(input);
    formulario.appendChild(boton);

    //atributos
    formulario.setAttribute("action","/agregarContacto");
    formulario.setAttribute("method","post");
    input.setAttribute("placeholder","Agregar número de telefono")
    input.setAttribute("minlength","10");
    input.setAttribute("maxlength","10");
    input.setAttribute("required", "required");
    input.setAttribute("type","number")
    boton.setAttribute("type", "submit")
    boton.innerText="Registrar";

}