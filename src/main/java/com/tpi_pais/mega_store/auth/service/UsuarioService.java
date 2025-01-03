package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.utils.EmailCodigoValidacion;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class UsuarioService implements IUsuarioService{

    private final UsuarioRepository modelRepository;

    private final ExpresionesRegulares expReg;

    private final StringUtils StringUtils;

    private final RolService rolService;

    private final SesionService sesionService;

    private final WebClient webClient;


    public UsuarioService(
            UsuarioRepository modelRepository,
            ExpresionesRegulares expReg,
            StringUtils stringUtils,
            RolService rolService,
            SesionService sesionService,
            WebClient webClient) {
        this.modelRepository = modelRepository;
        this.expReg = expReg;
        this.StringUtils = stringUtils;
        this.rolService = rolService;
        this.sesionService = sesionService;
        this.webClient = webClient;
    }

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
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
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
            throw new NotFoundException(MessagesException.OBJECTO_INEXISTENTE);
        }
        if (!model.get().esEliminado()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ELIMINADO);
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
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
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
            throw new NotFoundException(MessagesException.OBJECTO_INEXISTENTE);
        }
        if (!model.get().esEliminado()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ELIMINADO);
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
            throw new BadRequestException(MessagesException.OBJECTO_ELIMINADO);
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
            throw new BadRequestException(MessagesException.OBJECTO_NO_ELIMINADO);
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
        this.StringUtils.verificarExistencia(password,"password");
        if (model.checkPassword(password)) {
            return true;
        } else {
            throw new BadRequestException(MessagesException.CONTRASENA_INCORRECTA);
        }

    }

    @Override
    public void setPassword(Usuario model, String password) {
        /*
         * Modifica la contrase;a de un usuario
         * si la contrase;a es nula lanza una excepcion
         * Si la contrase;a enviada no coincide con la original lanza una excepcion
         * */
        this.StringUtils.verificarExistencia(password,"password");
        if (model.checkPassword(password)) {
            model.setPassword(password);
            modelRepository.save(model);
        } else {
            throw new BadRequestException(MessagesException.CONTRASENA_INCORRECTA);
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
        if (Objects.equals(metodo, "POST")){
            this.StringUtils.verificarExistencia(nombre, "nombre");
        }

        if (!this.expReg.verificarCaracteres(nombre)){
            throw new BadRequestException(MessagesException.CARACTERES_INVALIDOS+"Nombre");
        }
        if (Objects.equals(this.expReg.corregirCadena(nombre), "")){
            throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Nombre");
        }
        this.StringUtils.verificarLargo(nombre, 1, 100, "nombre");
    }

    @Override
    public void verificarEmail(String email) {
        this.StringUtils.verificarExistencia(email, "email");
        if (!this.expReg.verificarEmail(email)){
            throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Email");
        }
        if (this.emailUtilizado(email)){
            throw new BadRequestException(MessagesException.EMAIL_UTILIZADO);
        }
        this.StringUtils.verificarLargo(email, 1, 100, "email");
    }

    @Override
    public boolean emailUtilizado (String email) {
        return modelRepository.findByEmail(email).isPresent();
    }


    @Override
    public void verificarTelefono(String telefono, String metodo) {
        this.StringUtils.verificarExistencia(telefono, "telefono");
        if (!this.expReg.verificarNumeros(telefono)){
            throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Telefono");
        }
        this.StringUtils.verificarLargo(telefono, 1, 15, "telefono");
    }

    @Override
    public void verificarDireccion(String direccion, String metodo) {
        this.StringUtils.verificarExistencia(direccion, "direccion");
        if (!this.expReg.verificarCaracteres(direccion)){
            throw new BadRequestException(MessagesException.CARACTERES_INVALIDOS+"Direccion");
        }
        if (Objects.equals(this.expReg.corregirCadena(direccion), "")){
            throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Direccion");
        }
        this.StringUtils.verificarLargo(direccion, 1, 100, "direccion");
    }

    @Override
    public void verificarRol(Integer rol) {
        if (rol == null){
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Rol");
        }
        if (!this.expReg.verificarNumeros(String.valueOf(rol))){
            throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Rol");
        }
        Rol r = rolService.buscarPorId(rol);
    }

    @Override
    public void verificarPassword(String password) {
        if (Objects.equals(password, "")){
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Password");
        }
        /*
        * Requisitos:
        * - Min 8 caracteres
        * - Una letra mayuscula
        * - Una letra minuscula
        * - Un numero
        *
        * */
        if (!this.expReg.verificarPassword(password)){
            throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Password");
        }
        this.StringUtils.verificarLargo(password, 8, 100, "password");
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
        return this.guardar(model);
    }

    @Override
    public void enviarCodigoVerificacion(String email, String codigoVerificacion) {
        //Agregar un try por si ocurre un error: No se pudo enviar el mail
        // Datos que deseas enviar en el cuerpo de la solicitud


        String emailHtmlTemplate = EmailCodigoValidacion.emailHtmlTemplate;
        // Reemplaza las variables en la plantilla HTML
        String emailContent = emailHtmlTemplate
                .replace("{LOGO_URL}", EmailCodigoValidacion.logoUrl)
                .replace("{CODIGO_VERIFICACION}", codigoVerificacion);

        // Aquí escapamos las comillas dobles dentro del contenido HTML
        String escapedEmailContent = emailContent.replace("\"", "\\\"");

        // Aquí formateamos el JSON correctamente
        String requestBody = String.format("""
        {
            "destinatario": "%s",
            "asunto": "Código de verificación",
            "cuerpo": \"%s\"
        }
        """, email, escapedEmailContent);

        // Token de autorización (reemplaza "your-token" por el token real)
        String token = "48cd4db4cf2acbb1a532528b71fadb202efe8af8";

        // Realizar la solicitud POST para enviar el correo
        String response = String.valueOf(webClient.post()
                .uri("/emailSender/enviar/")  // Especifica el endpoint correcto
                .header("Content-Type", "application/json")
                .header("Authorization", "Token " + token)  // Agrega el token al encabezado
                .bodyValue(requestBody)  // Cuerpo de la solicitud con los datos de email y código
                .retrieve()
                .toEntity(String.class)
                .doOnTerminate(() -> {
                })
                .doOnError(error -> {
                })
                .block());  // Espera la respuesta sin bloquear el hilo principal
    }


    @Override
    public void verificarCodigoVerificacion(String email, String codigoVerificacion){
        Optional<Usuario> model = modelRepository.findByEmail(email);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_INEXISTENTE);
        }

        if (model.get().getVerificado()) {
            throw new BadRequestException(MessagesException.OBJETO_ACTIVO);
        }
        if (Duration.between(model.get().getFechaCreacion(), LocalDateTime.now()).toMinutes() > 15) {
            throw new BadRequestException(MessagesException.CODIGO_ACTIVACION_EXPIRADO);

        }
        if (model.get().getCodigoVerificacion().equals(codigoVerificacion)) {
            model.get().setVerificado(true);
            model.get().setCodigoVerificacion(null);
            modelRepository.save(model.get());
        }
        else {
            throw new BadRequestException(MessagesException.CODIGO_ACTIVACION_INCORRECTO);
        }
    }

    public Sesion login (UsuarioDTO usuarioDto) {
        Optional<Usuario> usuario = Optional.ofNullable(this.buscarPorEmail(usuarioDto.getEmail()));
        if (usuario.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_INEXISTENTE);
        }
        if (!this.checkPassword(usuario.get(), usuarioDto.getPassword())) {
            throw new BadRequestException(MessagesException.CONTRASENA_INCORRECTA);
        }
        return this.sesionService.obtenerSesionActual(usuario.get());
    }

}
