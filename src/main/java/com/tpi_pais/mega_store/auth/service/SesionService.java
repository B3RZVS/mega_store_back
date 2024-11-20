package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.SesionRepository;
import com.tpi_pais.mega_store.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SesionService  implements ISesionService {
    @Autowired
    private SesionRepository sesionRepository;

    @Override
    public Sesion obtenerSesionPorToken(String token) {
        Optional<Sesion> sesion = sesionRepository.findByFechaEliminacionIsNullAndToken(token);
        if (sesion.isEmpty()){
            throw new UnauthorizedException("No se encontro la sesion correspondiente al token");
        }
        if (sesion.get().esActivo()) {
            return sesion.get();
        }else {
            sesion.get().eliminar();
            sesionRepository.save(sesion.get());
            throw new UnauthorizedException("La sesion ya expiro");
        }
    }
    @Override
    public Sesion crearSesion(Usuario usuario) {
        Sesion sesion = new Sesion();
        sesion.setUsuario(usuario);
        sesionRepository.save(sesion);
        return sesion;
    }
    @Override
    public Sesion obtenerSesionActual (Usuario usuario) {
        Optional<Sesion> sesion = sesionRepository.findByUsuarioAndFechaEliminacionIsNull(usuario);
        if (sesion.isEmpty()){
            return crearSesion (usuario);
        }
        if (sesion.get().esActivo()) {
            return sesion.get();
        }else {
            sesion.get().eliminar();
            sesionRepository.save(sesion.get());
            return crearSesion (usuario);
        }
    }
    @Override
    public void eliminarSesion (String token) {
        Sesion sesion = obtenerSesionPorToken(token);
        sesion.eliminar();
        sesionRepository.save(sesion);
    }

    @Override
    public Rol obtenerRol(Sesion sesion) {
        Usuario usuario = sesion.getUsuario();
        Rol rol = usuario.getRol();
        if (rol != null) {
            return rol;
        }else {
            RolService rolService = new RolService();
            return rolService.buscarPorId(4);
        }
    }

}
