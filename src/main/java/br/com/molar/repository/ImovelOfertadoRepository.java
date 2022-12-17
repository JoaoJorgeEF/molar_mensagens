package br.com.molar.repository;

import br.com.molar.entities.ImovelOfertado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelOfertadoRepository extends JpaRepository<ImovelOfertado, Long> {
    @Query(value = "select * from imoveis_ofertados idj where idj.usuario_id = :idUsuario", nativeQuery = true)
    List<ImovelOfertado> findByIdUsuario(long idUsuario);

    @Query(value = "select * from imoveis_ofertados idj where idj.usuario_id <> :idUsuario", nativeQuery = true)
    List<ImovelOfertado> findDifferentFromIdUsuario(int idUsuario);
}
