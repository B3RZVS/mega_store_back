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
@Service
public class ImageBBService {

    private static final String API_URL = "https://api.imgbb.com/1/upload";
    private static final String API_KEY = "c01631214212f30966500c874875dcc8";

    public String subirImagen(MultipartFile archivo) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Verificar si el archivo es válido
            if (archivo == null || archivo.isEmpty()) {
                throw new BadRequestException("El archivo está vacío o es nulo.");
            }
            String base64Imagen = "";
            try {
                base64Imagen = java.util.Base64.getEncoder().encodeToString(archivo.getBytes());
                System.out.println("Base64 Imagen: " + base64Imagen.substring(0, 100) + "...");
            } catch (Exception e) {
                throw new BadRequestException("Error al convertir la imagen a Base64: " + e.getMessage());
            }

            // Crear la URL con la clave como parámetro de consulta
            String urlConClave = API_URL + "?key=" + API_KEY;

            // Crear el cuerpo de la solicitud
            MultiValueMap<String, String> cuerpoSolicitud = new LinkedMultiValueMap<>();
            cuerpoSolicitud.add("image", base64Imagen);

            // Configurar los encabezados
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Crear la entidad HTTP
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(cuerpoSolicitud, headers);

            // Enviar la solicitud a ImgBB
            try {
                ResponseEntity<Map> response = restTemplate.exchange(
                        urlConClave,           // URL con clave incluida
                        HttpMethod.POST,       // Método HTTP POST
                        requestEntity,         // Cuerpo de la solicitud
                        Map.class              // Tipo esperado de la respuesta
                );

                // Extraer la URL de la imagen desde la respuesta
                Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
                return data.get("display_url").toString();
            } catch (Exception e) {
                System.err.println("Error en la solicitud: " + e.getMessage());
                throw new BadRequestException("Error al subir la imagen a ImgBB: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error en la solicitud: " + e.getMessage());
            throw new BadRequestException("Error al subir la imagen a ImgBB: " + e.getMessage());
        }
    }
}
