package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Carrito;
import modelo.Usuario; // Asegúrate de importar tu modelo de usuario
import control.servicios.ServiciosCarrito;
import control.servicios.ServiciosUsuario; // Asegúrate de importar tu servicio de usuarios

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String email = request.getParameter("username"); 
        String clave = request.getParameter("password");

       
        Usuario usuario = ServiciosUsuario.validarUsuario(email, clave);

        if (usuario != null) {
        	request.getSession().setAttribute("usuario", usuario);
        	System.out.println("usuario validado");
        	
        	//comprobar si el usuario logeado tiene un carrito 
        	List<Carrito>carritoUser=ServiciosCarrito.comprobarCarrito(usuario.getId());
        	   if (carritoUser != null && !carritoUser.isEmpty()) {
                   request.getSession().setAttribute("carrito", carritoUser);
               }
        	
        	response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("errorMessage", "Usuario o contraseña incorrectos");
            System.out.println("datos incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response); 
        }
    }
}

