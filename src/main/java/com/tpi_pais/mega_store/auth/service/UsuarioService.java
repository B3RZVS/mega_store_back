package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository modelRepository;

    @Autowired
    private ExpresionesRegulares expReg;

    @Autowired
    private RolService rolService;

    @Autowired
    private WebClient webClient;

    @Override
    public List<UsuarioDTO> listar() {
        /*
        *  Trae todos los usuarios activos y no eliminados en orden ascendente
        * */
        List<Usuario> categorias = modelRepository.findByFechaEliminacionIsNullAndVerificadoTrueOrderByIdAsc();
        return categorias.stream().map(UsuarioMapper::toDTO).toList();
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        /*
         * Trae el usuario por su id si no lo encuentra lanza una excepcion
         * Si esta eliminado o inactivo lanza una excepcion
         * */
        Optional<Usuario> model = modelRepository.findByFechaEliminacionIsNullAndVerificadoTrueAndId(id);
        if (model.isEmpty()) {
            throw new NotFoundException("El usuario con el id " + id + " no existe o no se encuentra activo.");
        }
        return model.get();
    }

    @Override
    public Usuario buscarEliminadoPorId(Integer id) {
        /*
         * Trae el usuario por su id si no lo encuentra lanza una excepcion
         * Si esta eliminado o no verificado lanza una excepcion
         * */
        Optional<Usuario> model = modelRepository.findById(id);
        if (model.isEmpty()) {
            throw new NotFoundException("El usuario con el id " + id + " no existe.");
        }
        if (!model.get().esEliminado()) {
            throw new NotFoundException("El usuario con el id " + id + " no se encuentra eliminado.");
        }
        return model.get();
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        /*
         * Trae el usuario por su email si no lo encuentra lanza una excepcion
         * Si esta eliminado o no verificado lanza una excepcion
         * */
        Optional<Usuario> model = modelRepository.findByFechaEliminacionIsNullAndVerificadoTrueAndEmail(email);
        if (model.isEmpty()) {
            throw new NotFoundException("El usuario con email " + email + " no existe o no se encuentra activo.");
        }
        return model.get();
    }
    @Override
    public Usuario buscarEliminadoPorEmail(String email) {
        /*
         * Trae el usuario por su email si no lo encuentra lanza una excepcion
         * Si esta eliminado o inactivo lanza una excepcion
         * */
        Optional<Usuario> model = modelRepository.findByEmail(email);
        if (model.isEmpty()) {
            throw new NotFoundException("El usuario con email " + email + " no existe.");
        }
        if (!model.get().esEliminado()) {
            throw new NotFoundException("El usuario con email " + email + " no se encuentra eliminado.");
        }
        return model.get();
    }

    @Override
    public UsuarioDTO guardar(UsuarioDTO modelDTO) {
        /*
         * Guarda un usuario a partir de un DTO
         * */
        Usuario model = UsuarioMapper.toEntity(modelDTO);
        return UsuarioMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Usuario guardar(Usuario model) {
        /*
         * Guarda un usuario a partir de un modelo
         * */
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Usuario model) {
        /*
         * Elimina un usuario a partir de un modelo y si ya fue eliminado lanza una excepcion
         * */
        if (model.esEliminado()) {
            throw new BadRequestException("El usuario con id " + model.getId() + " ya fue eliminado.");
        }
        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Usuario model) {
        /*
         * Recupera un usuario a partir de un modelo y si ya fue recuperado lanza una excepcion
         * */
        if (!model.esEliminado()) {
            throw new BadRequestException("El usuario con id " + model.getId() + " no se encuentra eliminado.");
        }
        model.recuperar();
        modelRepository.save(model);
    }

    @Override
    public Boolean checkPassword(Usuario model, String password) {
        /*
         * Verifica si la contrase;a enviada coincide con la original
         * Si la contrase;a no coincide lanza una excepcion
         * Si la contrase;a es nula lanza una excepcion
         * */
        if (password == null) {
            throw new BadRequestException("La contrase;a no puede ser nula.");
        }
        if (model.checkPassword(password)) {
            return true;
        } else {
            throw new BadRequestException("La contrase;a es incorrecta.");
        }

    }

    @Override
    public void setPassword(Usuario model, String password) {
        /*
         * Modifica la contrase;a de un usuario
         * si la contrase;a es nula lanza una excepcion
         * Si la contrase;a enviada no coincide con la original lanza una excepcion
         * */
        if (password != null) {
            if (model.checkPassword(password)) {
                model.setPassword(password);
                modelRepository.save(model);
            } else {
                throw new BadRequestException("La contrase;a enviada no coincide con la original.");
            }
        } else {
            throw new BadRequestException("La contrase;a no puede ser nula.");
        }
    }

    @Override
    public void verificarAtributos(UsuarioDTO modelDTO) {
        // Nombre: Verifico y lo corrijo
        this.verificarNombre(modelDTO.getNombre(), "POST");
        // Email
        this.verificarEmail(modelDTO.getEmail());
        // Numero de Telefono
        this.verificarTelefono(modelDTO.getTelefono(), "POST");
        // Direccion de envio: Verifico y lo corrijo
        this.verificarDireccion(modelDTO.getDireccionEnvio(), "POST");
        //Rol
        this.verificarRol(modelDTO.getRolId());
        //Password
        this.verificarPassword(modelDTO.getPassword());
    }

    @Override
    public void verificarNombre(String nombre, String metodo) {
        if (metodo=="POST"){
            if (Objects.equals(nombre, "")){
                throw new BadRequestException("Se debe enviar un nombre");
            }
        }

        if (!this.expReg.verificarCaracteres(nombre)){
            throw new BadRequestException("El nombre no puede contener caracteres especiales.");
        }
        if (Objects.equals(this.expReg.corregirCadena(nombre), "")){
            throw new BadRequestException("El formato del nombre es inválido.");
        }
    }

    @Override
    public void verificarEmail(String email) {
        if (Objects.equals(email, "")){
            throw new BadRequestException("Se debe enviar un email");
        }
        if (!this.expReg.verificarEmail(email)){
            throw new BadRequestException("El formato del email es inválido.");
        }
        if (this.emailUtilizado(email)){
            throw new BadRequestException("El email ya se encuentra registrado.");
        }
    }

    @Override
    public boolean emailUtilizado (String email) {
        return modelRepository.findByEmail(email).isPresent();
    }


    @Override
    public void verificarTelefono(String telefono, String metodo) {
        if (Objects.equals(telefono, "")){
            throw new BadRequestException("Se debe enviar un telefono");
        }
        if (!this.expReg.verificarNumeros(telefono)){
            throw new BadRequestException("El formato del telefono es inválido, debe contener solo numeros.");
        }
    }

    @Override
    public void verificarDireccion(String direccion, String metodo) {
        if (Objects.equals(direccion, "")){
            throw new BadRequestException("Se debe enviar un direccion");
        }
        if (!this.expReg.verificarCaracteres(direccion)){
            throw new BadRequestException("La direccion no puede contener caracteres especiales.");
        }
        if (Objects.equals(this.expReg.corregirCadena(direccion), "")){
            throw new BadRequestException("El formato de la direccion es inválido.");
        }
    }

    @Override
    public void verificarRol(Integer rol) {
        if (rol == null){
            throw new BadRequestException("Se debe enviar un rol");
        }
        Rol r = rolService.buscarPorId(rol);
    }

    @Override
    public void verificarPassword(String password) {
        if (Objects.equals(password, "")){
            throw new BadRequestException("Se debe enviar una contrase;a");
        }
    }

    @Override
    public Usuario crearUsuario (UsuarioDTO modelDTO){
        //Creo el usuario
        Usuario model = new Usuario();
        //Nombre
        model.setNombre(this.expReg.corregirCadena(modelDTO.getNombre()));
        //Email
        model.setEmail(modelDTO.getEmail());
        //Telefono
        model.setTelefono(modelDTO.getTelefono());
        //Direccion
        model.setDireccionEnvio(this.expReg.corregirCadena(modelDTO.getDireccionEnvio()));
        //Rol
        model.setRol(rolService.buscarPorId(modelDTO.getRolId()));
        //Fecha Creacion
        model.setFechaCreacion();
        //Codigo Verificacion
        model.setCodigoVerificacion();
        //Verificado: Por ahora como falta enviar el codigo lo seteo en True
        model.setVerificado(false);
        //Password
        model.setPassword(modelDTO.getPassword());
        // Guardo el model
        Usuario modelGuardado = this.guardar(model);
        return modelGuardado;
    }

    @Override
    public void enviarCodigoVerificacion (String email, String codigoVerificacion){
        // Datos que deseas enviar en el cuerpo de la solicitud
        String requestBody = String.format("""
        {
            "destinatario": "%s",
            "asunto": "Código de verificación",
            "cuerpo": "%s"
        }
        """, email, codigoVerificacion);

        // Token de autorización (reemplaza "your-token" por el token real)
        String token = "48cd4db4cf2acbb1a532528b71fadb202efe8af8";

        // Realizar la solicitud POST para enviar el correo
        String response = webClient.post()
                .uri("/emailSender/enviar/")  // Especifica el endpoint correcto de tu API
                .header("Content-Type", "application/json")
                .header("Authorization", "Token " + token)  // Agrega el token al encabezado
                .bodyValue(requestBody)  // Cuerpo de la solicitud con los datos de email y código
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
