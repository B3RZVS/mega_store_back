package com.tpi_pais.mega_store.utils;

import com.tpi_pais.mega_store.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class StringUtils {
    public String capitalizeWords(String input) {
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

    public String limpiarToken (String token) {
        return token.replace("Token ", "");
    }

    public void verificarLargo (String input, int min, int max, String nombre) {
        if (input.length() < min){
            throw new BadRequestException("El campo " + nombre + " debe tener al menos " + min + " caracteres.");
        } else if (input.length() > max){
            throw new BadRequestException("El campo " + nombre + " debe tener menos de " + max + " caracteres.");
        }
    }

    public void verificarExistencia (String input, String nombre) {
        if (input == null || input.isEmpty()){
            throw new BadRequestException("El campo " + nombre + " es requerido.");
        }
    }
}
