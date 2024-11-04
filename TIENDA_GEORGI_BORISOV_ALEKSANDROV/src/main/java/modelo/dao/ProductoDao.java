package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class ProductoDao {

    // Método para agregar un nuevo producto
    public static boolean addProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre, descripcion, precio, impuesto, stock, baja, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getImpuesto(), producto.getStock(), producto.isBaja(), producto.getImagen())) {
            pstmt.executeUpdate(); // Ejecuta la inserción
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un producto por ID
    public static Producto getProductoById(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        Producto producto = null;
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, id);
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            if (rs != null && rs.next()) {
                producto = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getDouble("impuesto"),
                    rs.getInt("stock"),
                    rs.getBoolean("baja"),
                    rs.getString("imagen") // Obtener la ruta de la imagen
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return producto;
    }

    // Método para obtener todos los productos
    public static List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql); // Obtiene el PreparedStatement
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            while (rs != null && rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getDouble("impuesto"),
                    rs.getInt("stock"),
                    rs.getBoolean("baja"),
                    rs.getString("imagen") // Obtener la ruta de la imagen
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return productos;
    }

    // Método para actualizar un producto
    public static boolean updateProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, impuesto = ?, stock = ?, baja = ?, imagen = ? WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getImpuesto(), producto.getStock(), producto.isBaja(), producto.getImagen(), producto.getId())) {
            pstmt.executeUpdate(); // Ejecuta la actualización
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un producto por ID
    public static boolean deleteProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, id)) {
            pstmt.executeUpdate(); // Ejecuta la eliminación
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


