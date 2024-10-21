package com.tpi_pais.mega_store.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
/*
* ForbiddenException sirve para manejar los errores de acceso denegado
* Por ejemplo: Cuando un usuario no tiene permiso para acceder a un recurso.
* */