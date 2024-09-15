package com.tpi_pais.mega_store.utils;
import java.util.regex.Pattern;
public class ExpresionesRegulares {

    private static final Pattern PATRON_NUMEROS = Pattern.compile("^\\d+$");
    private static final Pattern PATRON_TEXTO = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern PATRON_TEXTO_ALFANUMERICO = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern PATRON_TEXTO_CON_ESPACIOS = Pattern.compile("^[A-Za-z]+(\\s[A-Za-z]+)*$");


    public boolean verificarNumeros(String cadena) {
        return PATRON_NUMEROS.matcher(cadena).matches();
    }


    public boolean verificarTexto(String cadena) {
        return PATRON_TEXTO.matcher(cadena).matches();
    }


    public boolean verificarTextoAlfanumerico(String cadena) {
        return PATRON_TEXTO_ALFANUMERICO.matcher(cadena).matches();
    }

    public boolean verificarTextoConEspacios(String cadena) {
        return PATRON_TEXTO_CON_ESPACIOS.matcher(cadena).matches();
    }

}
