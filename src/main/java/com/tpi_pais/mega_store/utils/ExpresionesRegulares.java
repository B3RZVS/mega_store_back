package com.tpi_pais.mega_store.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para manejar expresiones regulares y validar cadenas.
 * Provee métodos útiles para verificar formatos como números, texto, emails, entre otros.
 */
@Data
@Service
public class ExpresionesRegulares {

    // Expresiones regulares para diversas validaciones
    private static final Pattern PATRON_NUMEROS = Pattern.compile("^\\d+$"); // Solo números
    private static final Pattern PATRON_TEXTO = Pattern.compile("^[A-Za-z]+$"); // Solo letras
    private static final Pattern PATRON_TEXTO_ALFANUMERICO = Pattern.compile("^[A-Za-z0-9]+$"); // Letras y números sin espacios
    private static final Pattern PATRON_TEXTO_CON_ESPACIOS = Pattern.compile("^[A-Za-z0-9áéíóúÁÉÍÓÚ]+(?:[\\s][A-Za-z0-9áéíóúÁÉÍÓÚ]+)*$"); // Texto con espacios
    private static final Pattern PATRON_CARACTERES_PERMITIDOS = Pattern.compile("^[A-Za-z0-9áéíóúÁÉÍÓÚÁÉÍÓÚ\\s]+$"); // Texto y espacios con caracteres válidos
    private static final Pattern PATRON_EMAIL = Pattern.compile("^(?!\\.)(?!.*\\.@)(?!.*\\.\\.)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"); // Formato de email
    private static final Pattern PATRON_VALIDACION_PASSWORD = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$"); // Contraseña segura

    /**
     * Verifica si el email cumple con el formato establecido.
     * @param email Email a verificar.
     * @return true si el email es válido, false en caso contrario.
     */
    public boolean verificarEmail(String email) {
        return email != null && PATRON_EMAIL.matcher(email).matches();
    }

    /**
     * Verifica si una cadena contiene solo números.
     * @param cadena Cadena a verificar.
     * @return true si contiene solo números, false en caso contrario.
     */
    public boolean verificarNumeros(String cadena) {
        return PATRON_NUMEROS.matcher(cadena).matches();
    }

    /**
     * Verifica si una cadena contiene solo texto (letras).
     * @param cadena Cadena a verificar.
     * @return true si contiene solo letras, false en caso contrario.
     */
    public boolean verificarTexto(String cadena) {
        return PATRON_TEXTO.matcher(cadena).matches();
    }

    /**
     * Verifica si una cadena contiene caracteres permitidos (letras, números y espacios).
     * @param cadena Cadena a verificar.
     * @return true si cumple con el patrón, false en caso contrario.
     */
    public boolean verificarCaracteres(String cadena) {
        return PATRON_CARACTERES_PERMITIDOS.matcher(cadena).matches();
    }

    /**
     * Elimina espacios innecesarios en una cadena.
     * @param cadena Cadena a limpiar.
     * @return Cadena sin espacios al inicio, al final y con un único espacio entre palabras.
     */
    public static String limpiarEspacios(String cadena) {
        // Eliminar los espacios al principio y al final
        String cadenaLimpia = cadena.trim();

        // Reemplazar múltiples espacios entre palabras con un solo espacio
        cadenaLimpia = cadenaLimpia.replaceAll("\\s+", " ");

        return cadenaLimpia;
    }

    /**
     * Corrige una cadena para que cumpla con un formato de texto con espacios.
     * @param cadena Cadena a corregir.
     * @return Cadena corregida o vacía si no es posible corregirla.
     */
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

    /**
     * Verifica si una cadena contiene texto alfanumérico (letras y números sin espacios).
     * @param cadena Cadena a verificar.
     * @return true si cumple con el patrón, false en caso contrario.
     */
    public boolean verificarTextoAlfanumerico(String cadena) {
        return PATRON_TEXTO_ALFANUMERICO.matcher(cadena).matches();
    }

    /**
     * Verifica si una cadena contiene texto con espacios.
     * @param cadena Cadena a verificar.
     * @return true si cumple con el patrón, false en caso contrario.
     */
    public boolean verificarTextoConEspacios(String cadena) {
        return PATRON_TEXTO_CON_ESPACIOS.matcher(cadena).matches();
    }

    /**
     * Verifica si una contraseña cumple con los criterios de seguridad.
     * Debe tener al menos 8 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial.
     * @param password Contraseña a verificar.
     * @return true si cumple con el patrón, false en caso contrario.
     */
    public boolean verificarPassword(String password) {
        return PATRON_VALIDACION_PASSWORD.matcher(password).matches();
    }
}
