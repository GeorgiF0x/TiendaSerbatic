package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Carrito;

public class CarritoDao {

    // Método para agregar un producto al carrito
    public static boolean addProductoAlCarrito(Carrito carrito) {
        String sql = "INSERT INTO carrito (usuario_id, producto_id, unidades, fecha_creacion) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, carrito.getUsuarioId(), carrito.getProductoId(), carrito.getUnidades())) {
            pstmt.executeUpdate(); // Ejecuta la inserción
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los productos en el carrito de un usuario
    public static  List<Carrito> getCarritoPorUsuario(int usuarioId) {
        List<Carrito> carritoList = new ArrayList<>();
        String sql = "SELECT * FROM carrito WHERE usuario_id = ?";
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, usuarioId); // Obtiene el PreparedStatement
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            while (rs != null && rs.next()) {
                Carrito carrito = Carrito.builder()
                        .id(rs.getInt("id"))
                        .usuarioId(rs.getInt("usuario_id"))
                        .productoId(rs.getInt("producto_id"))
                        .unidades(rs.getInt("unidades"))
                        .fechaCreacion(rs.getTimestamp("fecha_creacion"))
                        .build();
                carritoList.add(carrito);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return carritoList;
    }

    // Método para eliminar un producto del carrito
    public static  boolean deleteProductoDelCarrito(int carritoId) {
        String sql = "DELETE FROM carrito WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, carritoId)) {
            pstmt.executeUpdate(); // Ejecuta la eliminación
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void updateUnidadesCarrito(int usuarioId, int productoId, int nuevasUnidades) {
        String sql = "UPDATE carrito SET unidades = ? WHERE usuario_id = ? AND producto_id = ?";
        
        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, nuevasUnidades, usuarioId, productoId); 
            int rowsUpdated = pstmt.executeUpdate(); 
            
            if (rowsUpdated > 0) {
                System.out.println("Unidades actualizadas exitosamente.");
            } else {
                System.out.println("No se encontró el producto en el carrito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(null, null); 
        }
    }

}

