package com.tpi_pais.mega_store.utils;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * Clase EmailCodigoValidacion
 * Representa la estructura de un correo electronico con un codigo de validacion.
 */

public class EmailCodigoValidacion {

    public static final String logoUrl = "https://i.ibb.co/NLytV57/logo.png";
    public static final String emailHtmlTemplate = """
            <!DOCTYPE html>
                       <html>
                       <head>
                           <meta charset="UTF-8">
                           <style>
                               body {
                                   font-family: Arial, sans-serif;
                                   color: #ffffff;
                                   margin: 0;
                                   padding: 0;
                                   background-color: #c99af3;
                               }
                               .container {
                                   width: 100%;
                                   max-width: 600px;
                                   margin: 0 auto;
                                   padding: 20px;
                                   background-color: #c99af3;
                               }
                               .header {
                                   text-align: center;
                                   background-color: #c99af3;
                                   padding: 10px;
                                   border: 4px solid white;
                                   border-radius: 15px 15px 0px 0px;
                               }
                               h1 {
                                 margin-top: 0px;
                               }
                               .content {
                                   background-color: #ffffff;
                                   padding: 30px;
                                   text-align: center;
                                   color: #6b2ba3;
                                   border-radius: 0px 0px 15px 15px;
                               }
                               .footer {
                                   text-align: center;
                                   font-size: 12px;
                                   color: #f0e6ff;
                                   padding-top: 20px;
                               }
                               .button {
                                   display: inline-block;
                                   background-color: #b071e4;
                                   color: white;
                                   padding: 10px 20px;
                                   text-decoration: none;
                                   border-radius: 5px;
                                   font-weight: bold;
                               }
                               .code {
                                   font-size: 24px;
                                   color: #b071e4;
                                   margin: 20px 0;
                               }
                               .logo {
                                   width: 100px;
                                   height: auto;
                                   margin-bottom: 20px;
                               }
                           </style>
                       </head>
                       <body>
                           <div class="container">
                               <div class="header">
                                   <img src="https://i.ibb.co/NLytV57/logo.png" alt="Mega Store Logo" class="logo" />
                                   <h1>Bienvenido a Mega Store</h1>
                               </div>
                               <div class="content">
                                   <h2 class="code">¡Hola, y bienvenido!</h2>
                                   <p>
                                       Gracias por unirte a Mega Store. Para completar el proceso de registro,\s
                                       por favor verifica tu dirección de correo electrónico usando el código a continuación:
                                   </p>
                                   <div class="code">{CODIGO_VERIFICACION}</div>
                                   <p>
                                       Introduce este código en la página de verificación para activar tu cuenta.
                                   </p>
                               </div>
                               <div class="footer">
                                   <p>Si no realizaste esta solicitud, puedes ignorar este correo.</p>
                                   <p>&copy; 2024 Mega Store. Todos los derechos reservados.</p>
                               </div>
                           </div>
                       </body>
                       </html>
    """;
}
