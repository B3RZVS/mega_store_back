package com.tpi_pais.mega_store.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
/*
* UnauthorizedException es una excepcion personalizada que se utiliza para manejar
* errores de autorizaciones.
* Por ejemplo : Cuando el usuario no se ha autenticado y quiere acceder a un recurso.
* */