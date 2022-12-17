package br.com.molar.entities;

import br.com.molar.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "imovel_ofertado_id")
    public ImovelOfertado imovelOfertado;

    @ManyToOne
    @JoinColumn(name = "imovel_desejado_id")
    public ImovelDesejado imovelDesejado;
}
