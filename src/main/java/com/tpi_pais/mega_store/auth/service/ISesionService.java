package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface ISesionService {
    public Sesion obtenerSesionPorToken(String token);

    public Sesion crearSesion (Usuario usuario);

    public Sesion obtenerSesionActual (Usuario usuario);

    public void eliminarSesion (String token);

}
