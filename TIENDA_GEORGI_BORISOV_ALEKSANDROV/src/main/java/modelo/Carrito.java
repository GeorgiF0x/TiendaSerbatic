

package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data // Genera automáticamente getters, setters, toString, equals, hashCode, y otros métodos
@NoArgsConstructor // Genera un constructor sin parámetros
@AllArgsConstructor // Genera un constructor con todos los parámetros
public class Carrito {
    private int id;                // ID del carrito
    private int usuarioId;         // ID del usuario que posee el carrito (puede ser null si es anónimo)
    private int productoId;        // ID del producto en el carrito
    private int unidades;          // Cantidad del producto en el carrito
    private Date fechaCreacion;    // Fecha en que se añadió al carrito
}

