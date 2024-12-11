package com.tpi_pais.mega_store.utils;

import com.tpi_pais.mega_store.exception.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * Clase utilitaria para manejar cadenas de texto.
 * Proporciona métodos para capitalizar palabras, limpiar tokens y validar propiedades de cadenas.
 */
@Service
public class StringUtils {

    /**
     * Capitaliza cada palabra de una cadena.
     * La primera letra de cada palabra se convierte en mayúscula, y el resto en minúsculas.
     *
     * @param input Cadena a capitalizar.
     * @return Cadena con las palabras capitalizadas o la cadena original si está vacía o nula.
     */
    public static String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+");
        StringBuilder capitalizedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalizedString.toString().trim(); // Elimina el espacio final
    }

    /**
     * Limpia un token eliminando el prefijo "Token ".
     *
     * @param token Token a limpiar.
     * @return Token sin el prefijo "Token ".
     */
    public String limpiarToken(String token) {
        return token.replace("Token ", "");
    }

    /**
     * Verifica que una cadena cumpla con un rango de longitud especificado.
     * Lanza una excepción si no cumple con las condiciones.
     *
     * @param input Cadena a verificar.
     * @param min Longitud mínima permitida.
     * @param max Longitud máxima permitida.
     * @param nombre Nombre del campo para usar en el mensaje de error.
     */
    public void verificarLargo(String input, int min, int max, String nombre) {
        if (input.length() < min) {
            throw new BadRequestException("El campo " + nombre + " debe tener al menos " + min + " caracteres.");
        } else if (input.length() > max) {
            throw new BadRequestException("El campo " + nombre + " debe tener menos de " + max + " caracteres.");
        }
    }

    /**
     * Verifica que una cadena no sea nula ni esté vacía.
     * Lanza una excepción si no cumple con la condición.
     *
     * @param input Cadena a verificar.
     * @param nombre Nombre del campo para usar en el mensaje de error.
     */
    public void verificarExistencia(String input, String nombre) {
        if (input == null || input.isEmpty()) {
            throw new BadRequestException("El campo " + nombre + " es requerido.");
        }
    }
}
