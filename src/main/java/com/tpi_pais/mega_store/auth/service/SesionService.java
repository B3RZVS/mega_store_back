package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.SesionRepository;
import com.tpi_pais.mega_store.exception.BadRequestException;
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
            throw new BadRequestException("No se encontro la sesion correspondiente al token");
        }
        if (sesion.get().esActivo()) {
            return sesion.get();
        }else {
            sesion.get().eliminar();
            sesionRepository.save(sesion.get());
            throw new BadRequestException("La sesion ya expiro");
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
}
