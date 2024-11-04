package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Detalle;

public class DetalleDao {

    // Método para agregar un nuevo detalle
    public static boolean addDetalle(Detalle detalle) {
        String sql = "INSERT INTO detalle (pedido_id, producto_id, unidades, preciounidad, impuesto, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, detalle.getPedidoId(), detalle.getProductoId(),
                detalle.getUnidades(), detalle.getPreciounidad(), detalle.getImpuesto(), detalle.getTotal())) {
            pstmt.executeUpdate(); // Ejecuta la inserción
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un detalle por ID
    public static Detalle getDetalleById(int id) {
        String sql = "SELECT * FROM detalle WHERE id = ?";
        Detalle detalle = null;
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, id);
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            if (rs != null && rs.next()) {
                detalle = new Detalle(
                    rs.getInt("id"),
                    rs.getInt("pedido_id"),
                    rs.getInt("producto_id"),
                    rs.getInt("unidades"),
                    rs.getDouble("preciounidad"),
                    rs.getDouble("impuesto"),
                    rs.getDouble("total")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return detalle;
    }

    // Método para obtener todos los detalles
    public static List<Detalle> getAllDetalles() {
        List<Detalle> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle";
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql); // Obtiene el PreparedStatement
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            while (rs != null && rs.next()) {
                detalles.add(new Detalle(
                    rs.getInt("id"),
                    rs.getInt("pedido_id"),
                    rs.getInt("producto_id"),
                    rs.getInt("unidades"),
                    rs.getDouble("preciounidad"),
                    rs.getDouble("impuesto"),
                    rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return detalles;
    }

    // Método para actualizar un detalle
    public boolean updateDetalle(Detalle detalle) {
        String sql = "UPDATE detalle SET pedido_id = ?, producto_id = ?, unidades = ?, preciounidad = ?, impuesto = ?, total = ? WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, detalle.getPedidoId(), detalle.getProductoId(),
                detalle.getUnidades(), detalle.getPreciounidad(), detalle.getImpuesto(), detalle.getTotal(), detalle.getId())) {
            pstmt.executeUpdate(); // Ejecuta la actualización
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un detalle por ID
    public static boolean deleteDetalle(int id) {
        String sql = "DELETE FROM detalle WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, id)) {
            pstmt.executeUpdate(); // Ejecuta la eliminación
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

