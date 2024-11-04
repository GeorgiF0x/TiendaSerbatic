package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detalle {
    private int id;
    private int pedidoId;
    private int productoId;
    private int unidades;
    private double preciounidad;
    private double impuesto;
    private double total;
}
