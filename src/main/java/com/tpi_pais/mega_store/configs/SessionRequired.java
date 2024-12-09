package com.tpi_pais.mega_store.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación @SessionRequired se utiliza para marcar métodos que requieren una sesión válida
 * antes de ser ejecutados. Esta anotación será procesada por un aspecto (AOP) para verificar
 * la validez de la sesión antes de permitir la ejecución del método.
 */

// Indica que esta anotación puede ser aplicada a métodos.
// Si se desea aplicar a clases, se puede cambiar a ElementType.TYPE.
@Target(ElementType.METHOD)

// Define que la anotación estará disponible en tiempo de ejecución,
// lo que permite que sea accesible durante la ejecución del programa (por AOP, por ejemplo).
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionRequired {
}
