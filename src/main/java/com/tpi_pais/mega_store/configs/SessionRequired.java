package com.tpi_pais.mega_store.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Puedes usar ElementType.TYPE si quieres aplicarla a clases enteras
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionRequired {
}
