package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/auth")

public class PutUsuarioController {
    @Autowired
    private IUsuarioService modelService;

    @Autowired
    private ResponseService responseService;

    @PutMapping("/usuario")
    public ResponseEntity<?> actualizar(@RequestBody UsuarioDTO modelDTO) {
        /*Descripción: Crear un endpoint PUT /usuarios/{id} en el controller que permita a los
        usuarios actualizar sus datos personales (nombre, dirección de envío, número de teléfono).
         El correo electrónico y la contraseña no podrán modificarse desde este endpoint.
    Controller: Implementar el método updateUser( UserDTO) que permita la
    actualización de los datos.
    Service: Implementar la lógica en el servicio para validar y actualizar los datos del
    usuario en la base de datos, asegurando que el correo no se modifique.
    Mapper: Usar el UserMapper para convertir el DTO actualizado a la entidad User.
    Model: Verificar que el modelo User permita la actualización de los campos correspondientes.
    DTO: El UserDTO contendrá los datos actualizables, excepto correo y contraseña.*/
        Usuario model = modelService.buscarPorId(modelDTO.getId());
        modelService.verificarNombre(modelDTO.getNombre(), "PUT");
        modelService.verificarDireccion(modelDTO.getDireccionEnvio(), "PUT");
        modelService.verificarTelefono(modelDTO.getTelefono(), "PUT");
        model.setNombre(modelDTO.getNombre());
        model.setDireccionEnvio(modelDTO.getDireccionEnvio());
        model.setTelefono(modelDTO.getTelefono());
        modelService.guardar(model);
        modelDTO = UsuarioMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "Usuario actualizado");
    }
}
