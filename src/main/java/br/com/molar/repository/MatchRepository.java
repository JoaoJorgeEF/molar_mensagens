package br.com.molar.repository;

import br.com.molar.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "select * from matches mt where mt.imovel_ofertado_id = :idImovelOfertado and mt.imovel_desejado_id = :idImovelDesejado", nativeQuery = true)
    Match findByIdImovelOfertadoEIdImovelDesejado(long idImovelOfertado, long idImovelDesejado);
}
