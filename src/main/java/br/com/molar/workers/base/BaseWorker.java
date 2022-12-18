package br.com.molar.workers.base;

import br.com.molar.entities.ImovelDesejado;
import br.com.molar.entities.ImovelOfertado;
import br.com.molar.entities.base.BaseEntity;
import br.com.molar.repository.ImovelDesejadoRepository;
import br.com.molar.repository.ImovelOfertadoRepository;
import br.com.molar.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseWorker {
    @Autowired
    public ImovelOfertadoRepository imovelOfertadoRepository;

    @Autowired
    public ImovelDesejadoRepository imovelDesejadoRepository;

    @Autowired
    public MatchRepository matchRepository;

    public boolean temMatch(ImovelOfertado imovelOfertado, ImovelDesejado imovelDesejado){
        if (imovelDesejado.getArea() <= imovelOfertado.getArea() &&
            imovelDesejado.getTipoImovel() == imovelOfertado.getTipoImovel() &&
            imovelDesejado.getBairro().equals(imovelOfertado.getBairro()) &&
            imovelDesejado.getPreco().compareTo(imovelOfertado.getPreco()) >= 0) {
            return true;
        }
        return false;
    }
}
