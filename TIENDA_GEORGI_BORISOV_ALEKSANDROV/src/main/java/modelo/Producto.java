package modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private double impuesto;
    private int stock;
    private boolean baja;
    private String imagen; 
}
