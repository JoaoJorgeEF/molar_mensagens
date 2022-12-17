package br.com.molar.entities;

import br.com.molar.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends BaseEntity<Integer> {
    public String logradouro;
    public String bairro;
    public String numero_residencia;
    public String cep;
}
