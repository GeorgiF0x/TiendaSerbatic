package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.servicios.ServiciosCarrito;
import modelo.Carrito;
import modelo.Producto;
import modelo.Usuario;

/**
 * Servlet implementation class CarritoController
 */
@WebServlet("/CarritoController")
public class CarritoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarritoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    String idProductoCadena = request.getParameter("productoId");
	    String cantidadCadena = request.getParameter("cantidad");
	    int idProducto = Integer.parseInt(idProductoCadena);
	    int cantidadAddCarrito = Integer.parseInt(cantidadCadena);
	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

	    // Verificar que los parámetros no son nulos antes de convertirlos
	    if (idProductoCadena == null || cantidadCadena == null) {
	        System.out.println("Error: uno o más parámetros son nulos");
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros necesarios");
	        return;
	    }
	    
	    try {
	        
	        // Verificar si el usuario está logueado

	        if (usuario == null) {
	            // Gestión del carrito anónimo
	            List<Carrito> carritoAnonimo = (List<Carrito>) request.getSession().getAttribute("carritoAnonimo");

	            if (carritoAnonimo == null) {
	                carritoAnonimo = new ArrayList<>();
	                request.getSession().setAttribute("carritoAnonimo", carritoAnonimo);
	            }

	            boolean productoExistente = false;
	            for (Carrito item : carritoAnonimo) {
	                if (item.getProductoId() == idProducto) {
	                    int cantidadAnterior = item.getUnidades();
	                    item.setUnidades(cantidadAnterior + cantidadAddCarrito);
	                    productoExistente = true;
	                    
	                    System.out.println("Producto actualizado en el carrito anónimo:");
	                    System.out.println("ID Producto: " + idProducto);
	                    System.out.println("Cantidad anterior: " + cantidadAnterior);
	                    System.out.println("Cantidad añadida: " + cantidadAddCarrito);
	                    System.out.println("Nueva cantidad: " + item.getUnidades());
	                    break;
	                }
	            }

	            if (!productoExistente) {
	                Carrito nuevoItem = new Carrito(0, 0, idProducto, cantidadAddCarrito, new Date());
	                carritoAnonimo.add(nuevoItem);
	                
	                System.out.println("Producto añadido al carrito anónimo:");
	                System.out.println("ID Producto: " + idProducto);
	                System.out.println("Cantidad añadida: " + cantidadAddCarrito);
	            }

	        } else {
	            // Gestión del carrito del usuario logueado
	            int usuarioId = usuario.getId();
	            List<Carrito> carritoUsuario = (List<Carrito>) request.getSession().getAttribute("carrito");

	            if (carritoUsuario == null) {
	                // Si no hay carrito en sesión, obtén el carrito desde la base de datos
	                carritoUsuario = ServiciosCarrito.comprobarCarrito(usuarioId);
	                request.getSession().setAttribute("carrito", carritoUsuario);
	            }

	            boolean productoExistente = false;
	            for (Carrito item : carritoUsuario) {
	                if (item.getProductoId() == idProducto) {
	                    int cantidadAnterior = item.getUnidades();
	                    item.setUnidades(cantidadAnterior + cantidadAddCarrito);
	                    ServiciosCarrito.actualizarUnidadesProducto(usuarioId, idProducto, item.getUnidades());
	                    productoExistente = true;
	                    
	                    System.out.println("Producto actualizado en el carrito del usuario:");
	                    System.out.println("ID Producto: " + idProducto);
	                    System.out.println("Cantidad anterior: " + cantidadAnterior);
	                    System.out.println("Cantidad añadida: " + cantidadAddCarrito);
	                    System.out.println("Nueva cantidad: " + item.getUnidades());
	                    break;
	                }
	            }

	            if (!productoExistente) {
	                Carrito nuevoItem = new Carrito(0, usuarioId, idProducto, cantidadAddCarrito, new Date());
	                carritoUsuario.add(nuevoItem);
	                ServiciosCarrito.agregarProductoAlCarrito(nuevoItem);
	                
	                System.out.println("Producto añadido al carrito del usuario:");
	                System.out.println("ID Producto: " + idProducto);
	                System.out.println("Cantidad añadida: " + cantidadAddCarrito);
	            }
	        }
	        
	    } catch (NumberFormatException e) {
	        System.out.println("Error: los parámetros no son números válidos");
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
	    }
	}



}
