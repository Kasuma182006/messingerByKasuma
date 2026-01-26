

document.addEventListener("DOMContentLoaded", function() {

    document.getElementById("formulario").addEventListener("submit",function(event){
        
        const contrasena = document.getElementById("contrasena").value;
        const confirmarContrasena = document.getElementById("confirmarContrasena").value;
        const numero = document.getElementById("id").value.toString();

         if (contrasena != confirmarContrasena ){
            event.preventDefault();
            alert("Las contraseñas no coinciden")
            console.log("no")
        }
        else {
            alert("cuenta registrada con exito")
             console.log("si")
        }            
    });


});






/*document.getElementById("formulario").addEventListener("submit",function(event){
    event.preventDefault();

    const registro = new FormData (event.target);
    
    if (registro.get("contrasena")== registro.get("confirmarContrasena")){

        const datos = {
            id: registro.get("id"),
            nombre: registro.get(nombre),
            contrasena: registro.get("contrasena")
        }


        fetch("/registro",{
            method : "POST",
            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(datos)
        })

        .then (response => response.text)
        .then (respuesta => {
            
        })

    }


})*/




