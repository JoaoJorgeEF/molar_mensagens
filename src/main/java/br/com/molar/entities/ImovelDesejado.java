package br.com.molar.entities;


import br.com.molar.entities.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imoveis_desejados")
public class ImovelDesejado extends BaseEntity<Long> {

    @Column(name = "tipo_imovel")
    @NotNull(message = "É necessário informar o tipo do imóvel desejado")
    public TipoImovel tipoImovel;

    @NotBlank(message = "É necessario informar o bairro")
    public String bairro;

    @Min(value = 30, message = "O valor da área deve ser igual ou maior a 30m².")
    public double area;

    @Column(name = "numero_quartos", nullable = false)
    @Min(value = 1, message = "A quantidade mínima de quartos deve ser 1.")
    public int numeroQuartos;

    @Column(name = "numero_banheiros", nullable = false)
    @Min(value = 1, message = "O número mínimo de banheiros deve ser 1.")
    public int numeroBanheiros;

    @Column(name = "numero_vagas_garagem", nullable = false)
    @NotNull( message = "É necessário informar o número de vagas de garagem, caso não queira, informe 0.")
    public int numeroVagasGaragem;

    @Column(nullable = false)
    @Min(value = 1, message = "O valor mínimo para preço é 1.")
    public BigDecimal preco;

    @NotNull(message = "É necessário informar o id do cliente")
    public int usuario_id;

    @Transient
    public Usuario usuario;
}
