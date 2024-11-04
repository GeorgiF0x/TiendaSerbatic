package control.servicios;

import java.util.List;

import modelo.Carrito;
import modelo.dao.CarritoDao;

public class ServiciosCarrito {

   
    public static List<Carrito> comprobarCarrito(int idCliente) {
        return CarritoDao.getCarritoPorUsuario(idCliente);
    }


    public static boolean agregarProductoAlCarrito(Carrito carrito) {
        return CarritoDao.addProductoAlCarrito(carrito);
    }


    public static boolean eliminarProductoDelCarrito(int carritoId) {
        return CarritoDao.deleteProductoDelCarrito(carritoId);
    }
    public static boolean actualizarUnidadesProducto(int usuarioId, int productoId, int nuevasUnidades) {
        if (nuevasUnidades < 0) {
            System.out.println("La cantidad de unidades no puede ser negativa.");
            return false;
        }
        try {
            CarritoDao.updateUnidadesCarrito(usuarioId, productoId, nuevasUnidades);
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar las unidades en el carrito: " + e.getMessage());
            return false;
        }
    }
}
