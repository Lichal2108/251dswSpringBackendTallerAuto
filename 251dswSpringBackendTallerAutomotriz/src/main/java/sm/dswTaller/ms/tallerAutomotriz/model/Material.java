/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.tallerAutomotriz.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "material")

public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "precio", nullable = false)
    private Double precio;


    public synchronized boolean reducirStock(int cantidad) {
        if (stock >= cantidad) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }

    public synchronized void restaurarStock(int cantidad) {
        this.stock += cantidad;
    }

    public synchronized void confirmarCompra(int cantidad) {
        if (this.stock >= cantidad) {
            this.stock -= cantidad;
        }
    }

    public synchronized void liberarStock(int cantidad) {
        this.restaurarStock(cantidad);
    }



}
