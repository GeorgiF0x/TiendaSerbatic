

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
    private int id;                
    private int usuarioId;         
    private int productoId;        
    private int unidades;          
    private Date fechaCreacion;    
}

