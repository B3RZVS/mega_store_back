package com.tpi_pais.mega_store.exception;

/**
 * Clase que contiene mensajes de excepción estáticos y predefinidos.
 * Estos mensajes se utilizan para proporcionar respuestas claras y consistentes
 * cuando se producen errores o eventos específicos en la aplicación.
 */
public class MessagesException {

    // Mensajes relacionados con objetos
    public static final String OBJECTO_NO_ENCONTRADO = "El objeto no ha sido encontrado";
    public static final String OBJECTO_ELIMINADO = "El objeto se encuentra eliminado";
    public static final String OBJECTO_INACTIVO = "El objeto se encuentra inactivo";
    public static final String OBJETO_ACTIVO = "El objeto se encuentra activo";
    public static final String OBJECTO_NO_ELIMINADO = "El objeto no se encuentra eliminado";
    public static final String OBJECTO_INEXISTENTE = "El objeto no existe";
    public static final String OBJETO_DUPLICADO = "El objeto ya se encuentra registrado";
    public static final String OBJETO_DUPLICADO_RECUPERADO = "Ya existía un objeto igual en la base de datos, objeto recuperado";

    // Mensajes relacionados con contraseñas
    public static final String CONTRASENA_INCORRECTA = "La contraseña es incorrecta";

    // Mensajes relacionados con caracteres y formatos
    public static final String CARACTERES_INVALIDOS = "Se utilizaron caracteres inválidos en el campo: ";
    public static final String FORMATO_INVALIDO = "Formato incorrecto en el campo: ";
    public static final String LONGITUD_INVALIDA = "Longitud inválida en el campo: ";

    // Mensajes relacionados con emails
    public static final String EMAIL_UTILIZADO = "El email ya se encuentra registrado";

    // Mensajes relacionados con campos
    public static final String CAMPO_NO_ENVIADO = "Se debe enviar el campo: ";
    public static final String CAMPO_NUMERICO_MAYOR_0 = "El valor numérico debe ser mayor a 0 en el campo: ";

    // Mensajes relacionados con código de activación
    public static final String CODIGO_ACTIVACION_EXPIRADO = "El código de activación ha expirado";
    public static final String CODIGO_ACTIVACION_INCORRECTO = "El código de activación es incorrecto";

    // Mensajes relacionados con tokens
    public static final String TOKEN_INVALIDO = "El token es inválido";
    public static final String TOKEN_EXPIRADO = "El token ha expirado";

    // Mensajes relacionados con stock
    public static final String STOCK_INSUFICIENTE = "El stock es insuficiente";
    public static final String STOCK_NEGATIVO = "El stock no puede ser negativo";

}
