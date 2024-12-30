package com.tpi_pais.mega_store.configs;

import com.tpi_pais.mega_store.exception.BadRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Servicio encargado de gestionar la carga de imágenes en el servicio ImgBB.
 * Este servicio toma un archivo de imagen, lo convierte a Base64 y lo sube a ImgBB utilizando su API.
 */
@Service
public class ImageBBService {

    // URL de la API ImgBB para la carga de imágenes
    private static final String API_URL = "https://api.imgbb.com/1/upload";
    // Clave de API para autenticar las solicitudes a ImgBB
    private static final String API_KEY = "c01631214212f30966500c874875dcc8";

    /**
     * Método principal para subir una imagen a ImgBB.
     *
     * @param archivo El archivo de imagen a subir.
     * @return La URL pública de la imagen subida.
     * @throws BadRequestException Si el archivo es nulo, vacío o hay un error durante el proceso.
     */
    public String subirImagen(MultipartFile archivo) {
        // Validar que el archivo no sea nulo ni vacío
        validarArchivo(archivo);

        // Convertir la imagen a formato Base64
        String base64Imagen = convertirImagenABase64(archivo);

        // Construir la URL para la API con la clave de autenticación
        String urlConClave = construirUrlConClave();

        // Enviar la imagen a ImgBB y obtener la URL de la imagen
        return enviarImagenAImgBB(base64Imagen, urlConClave);
    }

    /**
     * Método para validar que el archivo de imagen no sea nulo ni vacío.
     *
     * @param archivo El archivo de imagen a validar.
     * @throws BadRequestException Si el archivo es nulo o vacío.
     */
    private void validarArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new BadRequestException("El archivo está vacío o es nulo.");
        }
    }

    /**
     * Método para convertir la imagen a formato Base64.
     *
     * @param archivo El archivo de imagen a convertir.
     * @return La imagen en formato Base64.
     * @throws BadRequestException Si ocurre un error durante la conversión a Base64.
     */
    private String convertirImagenABase64(MultipartFile archivo) {
        try {
            return java.util.Base64.getEncoder().encodeToString(archivo.getBytes());
        } catch (Exception e) {
            throw new BadRequestException("Error al convertir la imagen a Base64: " + e.getMessage());
        }
    }

    /**
     * Método para construir la URL completa con la clave de API para la carga de imágenes.
     *
     * @return La URL completa con la clave de API.
     */
    private String construirUrlConClave() {
        return API_URL + "?key=" + API_KEY;
    }

    /**
     * Método para enviar la imagen a ImgBB y obtener la URL de la imagen cargada.
     *
     * @param base64Imagen La imagen en formato Base64.
     * @param urlConClave La URL con la clave de API.
     * @return La URL de la imagen subida.
     * @throws BadRequestException Si ocurre un error durante el proceso de carga de la imagen.
     */
    private String enviarImagenAImgBB(String base64Imagen, String urlConClave) {
        RestTemplate restTemplate = new RestTemplate();

        // Crear el cuerpo de la solicitud con el archivo en Base64
        MultiValueMap<String, String> cuerpoSolicitud = new LinkedMultiValueMap<>();
        cuerpoSolicitud.add("image", base64Imagen);

        // Configurar los encabezados para la solicitud HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Crear la entidad HTTP con el cuerpo y los encabezados
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(cuerpoSolicitud, headers);

        try {
            // Enviar la solicitud POST a ImgBB y recibir la respuesta
            ResponseEntity<Map> response = restTemplate.exchange(
                    urlConClave,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Extraer la URL de la imagen desde la respuesta de la API
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return data.get("display_url").toString(); // Retorna la URL pública de la imagen subida.
        } catch (Exception e) {
            throw new BadRequestException("Error al subir la imagen a ImgBB: " + e.getMessage());
        }
    }
}
