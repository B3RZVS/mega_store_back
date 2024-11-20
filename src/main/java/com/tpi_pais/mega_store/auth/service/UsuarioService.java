package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
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

    @Autowired
    private UsuarioRepository modelRepository;

    @Autowired
    private ExpresionesRegulares expReg;

    @Autowired
    private StringUtils StringUtils;

    @Autowired
    private RolService rolService;

    @Autowired
    private SesionService sesionService;

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
        this.StringUtils.verificarExistencia(password,"password");
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
        this.StringUtils.verificarExistencia(password,"password");
        if (model.checkPassword(password)) {
            model.setPassword(password);
            modelRepository.save(model);
        } else {
            throw new BadRequestException("La contrase;a enviada no coincide con la original.");
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
            throw new BadRequestException("El nombre no puede contener caracteres especiales.");
        }
        if (Objects.equals(this.expReg.corregirCadena(nombre), "")){
            throw new BadRequestException("El formato del nombre es inválido.");
        }
        this.StringUtils.verificarLargo(nombre, 1, 100, "nombre");
    }

    @Override
    public void verificarEmail(String email) {
        this.StringUtils.verificarExistencia(email, "email");
        if (!this.expReg.verificarEmail(email)){
            throw new BadRequestException("El formato del email es inválido.");
        }
        if (this.emailUtilizado(email)){
            throw new BadRequestException("El email ya se encuentra registrado.");
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
            throw new BadRequestException("El formato del telefono es inválido, debe contener solo numeros.");
        }
        this.StringUtils.verificarLargo(telefono, 1, 15, "telefono");
    }

    @Override
    public void verificarDireccion(String direccion, String metodo) {
        this.StringUtils.verificarExistencia(direccion, "direccion");
        if (!this.expReg.verificarCaracteres(direccion)){
            throw new BadRequestException("La direccion no puede contener caracteres especiales.");
        }
        if (Objects.equals(this.expReg.corregirCadena(direccion), "")){
            throw new BadRequestException("El formato de la direccion es inválido.");
        }
        this.StringUtils.verificarLargo(direccion, 1, 100, "direccion");
    }

    @Override
    public void verificarRol(Integer rol) {
        if (rol == null){
            throw new BadRequestException("Se debe enviar un rol");
        }
        if (!this.expReg.verificarNumeros(String.valueOf(rol))){
            throw new BadRequestException("El formato del rol es inválido.");
        }
        Rol r = rolService.buscarPorId(rol);
    }

    @Override
    public void verificarPassword(String password) {
        if (Objects.equals(password, "")){
            throw new BadRequestException("Se debe enviar una contrase;a");
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
            throw new BadRequestException("La contraseña no cumple con el formato necesario.");
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
        String logoUrl = "https://i.ibb.co/NLytV57/logo.png";

        String emailHtmlTemplate = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><style>body {font-family: Arial, sans-serif;color: #ffffff;margin: 0;padding: 0;background-color: #c17eff;} .container {width: 100%;max-width: 600px;margin: 0 auto;padding: 20px;background-color: #c17eff;} .header {text-align: center;background-color: #b071e4;padding: 20px;} .content {background-color: #ffffff;padding: 30px;text-align: center;color: #6b2ba3;} .footer {text-align: center;font-size: 12px;color: #f0e6ff;padding-top: 20px;} .button {display: inline-block;background-color: #b071e4;color: white;padding: 10px 20px;text-decoration: none;border-radius: 5px;font-weight: bold;} .code {font-size: 24px;color: #b071e4;margin: 20px 0;} .logo {width: 100px;height: auto;margin-bottom: 20px;}</style></head><body><div class=\"container\"><div class=\"header\"><img src=\"{LOGO_URL}\" alt=\"Mega Store Logo\" class=\"logo\"/><h1>Bienvenido a Mega Store</h1></div><div class=\"content\"><h2>¡Hola, y bienvenido!</h2><p>Gracias por unirte a Mega Store. Para completar el proceso de registro, por favor verifica tu dirección de correo electrónico usando el código a continuación:</p><div class=\"code\">{CODIGO_VERIFICACION}</div><p>Introduce este código en la página de verificación para activar tu cuenta.</p><a href=\"https://www.megastore.com/verify\" class=\"button\">Verificar mi cuenta</a></div><div class=\"footer\"><p>Si no realizaste esta solicitud, puedes ignorar este correo.</p><p>&copy; 2024 Mega Store. Todos los derechos reservados.</p></div></div></body></html>";

        // Reemplaza las variables en la plantilla HTML
        String emailContent = emailHtmlTemplate
                .replace("{LOGO_URL}", logoUrl)
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

        System.out.println(requestBody);

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
                    System.out.println("Correo enviado correctamente.");
                })
                .doOnError(error -> {
                    System.out.println("Error al enviar el correo: " + error.getMessage());
                })
                .block());  // Espera la respuesta sin bloquear el hilo principal


        System.out.println(response);
    }


    @Override
    public void verificarCodigoVerificacion(String email, String codigoVerificacion){
        Optional<Usuario> model = modelRepository.findByEmail(email);
        if (model.isEmpty()) {
            throw new NotFoundException("El usuario con email " + email + " no existe.");
        }

        if (model.get().getVerificado()) {
            throw new BadRequestException("El usuario con email " + email + " ya se encuentra verificado.");
        }
        if (Duration.between(model.get().getFechaCreacion(), LocalDateTime.now()).toMinutes() > 15) {
            throw new BadRequestException("El código de verificación ha expirado.");

        }
        if (model.get().getCodigoVerificacion().equals(codigoVerificacion)) {
            model.get().setVerificado(true);
            model.get().setCodigoVerificacion(null);
            modelRepository.save(model.get());
        }
        else {
            throw new BadRequestException("El codigo de verificacion es incorrecto.");
        }
    }

    public Sesion login (UsuarioDTO usuarioDto) {
        Optional<Usuario> usuario = Optional.ofNullable(this.buscarPorEmail(usuarioDto.getEmail()));
        if (usuario.isEmpty()) {
            throw new NotFoundException("El usuario con email " + usuarioDto.getEmail() + " no existe.");
        }
        if (!this.checkPassword(usuario.get(), usuarioDto.getPassword())) {
            throw new BadRequestException("La contrase;a es incorrecta.");
        }
        return this.sesionService.obtenerSesionActual(usuario.get());
    }

}
