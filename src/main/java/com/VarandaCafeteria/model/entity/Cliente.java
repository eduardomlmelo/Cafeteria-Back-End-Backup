package com.VarandaCafeteria.model.entity;

import com.VarandaCafeteria.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    private Double carteiraDigital;
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    @Enumerated(EnumType.STRING)
    private Role role;
}
