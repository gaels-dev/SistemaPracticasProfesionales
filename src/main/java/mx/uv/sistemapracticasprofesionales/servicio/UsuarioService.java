package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
import mx.uv.sistemapracticasprofesionales.excepciones.CredencialesInvalidasException;
import mx.uv.sistemapracticasprofesionales.modelo.dao.UsuarioDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;
import mx.uv.sistemapracticasprofesionales.utilidades.HasheoContrasenia;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Servicio para gestionar la lógica de negocio de los usuarios.
 */
public class UsuarioService {
 
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
 
    public Usuario autenticar(String nombre, String contrasenia)
            throws CredencialesInvalidasException, SQLException {
 
        if (nombre == null || nombre.trim().isEmpty() ||
            contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new CredencialesInvalidasException("Usuario y contraseña son obligatorios.");
        }
 
        Usuario usuario = usuarioDAO.buscarPorNombre(nombre.trim());
 
        if (usuario == null) {
            throw new CredencialesInvalidasException("No se encontró usuario activo con nombre: " + nombre);
        }
 
        String hashIngresado = HasheoContrasenia.hashPassword(contrasenia);
        if (!hashIngresado.equals(usuario.getContrasenia())) {
            throw new CredencialesInvalidasException("Contraseña incorrecta para usuario: " + nombre);
        }
 
        return usuario;
    }
 
    public Usuario obtenerPorId(int idUsuario) throws SQLException {
        return usuarioDAO.obtenerPorId(idUsuario);
    }
}
