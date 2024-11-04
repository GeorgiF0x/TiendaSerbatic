package control.servicios;

import java.util.List;

import modelo.Producto;
import modelo.dao.ProductoDao;
public class ServiciosProducto {
	
	   static public List<Producto> obtenerCatalogo() {
	        return ProductoDao.getAllProductos(); // Llama al DAO para obtener la lista de productos
	    }
}
