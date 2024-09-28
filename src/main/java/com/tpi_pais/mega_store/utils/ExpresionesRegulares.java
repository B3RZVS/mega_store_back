package com.tpi_pais.mega_store.utils;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ExpresionesRegulares {

    private static final Pattern PATRON_NUMEROS = Pattern.compile("^\\d+$");
    private static final Pattern PATRON_TEXTO = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern PATRON_TEXTO_ALFANUMERICO = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern PATRON_TEXTO_CON_ESPACIOS = Pattern.compile("^[A-Za-z0-9áéíóúÁÉÍÓÚÁÉÍÓÚ]+([\\s\\-_.A-Za-z0-9áéíóúÁÉÍÓÚÁÉÍÓÚ]+)*$");
    private static final Pattern PATRON_CARACTERES_PERMITIDOS = Pattern.compile("^[A-Za-z0-9áéíóúÁÉÍÓÚÁÉÍÓÚ\\s]+$");



    public boolean verificarNumeros(String cadena) {
        return PATRON_NUMEROS.matcher(cadena).matches();
    }


    public boolean verificarTexto(String cadena) {
        return PATRON_TEXTO.matcher(cadena).matches();
    }

    public boolean verificarCaracteres(String cadena) {
        return PATRON_CARACTERES_PERMITIDOS.matcher(cadena).matches();
    }
    public static String limpiarEspacios(String cadena) {
        // Eliminar los espacios al principio y al final
        String cadenaLimpia = cadena.trim();

        // Reemplazar múltiples espacios entre palabras con un solo espacio
        cadenaLimpia = cadenaLimpia.replaceAll("\\s+", " ");

        return cadenaLimpia;
    }
    public String corregirCadena(String cadena) {
        Pattern pattern = Pattern.compile(String.valueOf(PATRON_TEXTO_CON_ESPACIOS));
        Matcher matcher = pattern.matcher(cadena);

        // Si la cadena ya cumple con la expresión regular, la retornamos
        if (matcher.matches()) {
            return cadena;
        }

        cadena = limpiarEspacios(cadena);

        // Eliminamos los caracteres no válidos
        StringBuilder cadenaCorregida = new StringBuilder();
        for (char c : cadena.toCharArray()) {
            if (Character.isLetter(c) || Character.isWhitespace(c)) {
                cadenaCorregida.append(c);
            }
        }

        // Volvemos a verificar si la cadena corregida cumple con la expresión regular
        matcher = pattern.matcher(cadenaCorregida.toString());
        if (matcher.matches()) {
            return cadenaCorregida.toString();
        }

        // Si no cumple del todo, retornamos una cadena vacía o un mensaje
        return "";
    }


    public boolean verificarTextoAlfanumerico(String cadena) {
        return PATRON_TEXTO_ALFANUMERICO.matcher(cadena).matches();
    }

    public boolean verificarTextoConEspacios(String cadena) {
        return PATRON_TEXTO_CON_ESPACIOS.matcher(cadena).matches();
    }

}
