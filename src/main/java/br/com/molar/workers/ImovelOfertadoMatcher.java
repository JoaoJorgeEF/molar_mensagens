package br.com.molar.workers;

import br.com.molar.entities.ImovelDesejado;
import br.com.molar.entities.ImovelOfertado;
import br.com.molar.entities.Match;
import br.com.molar.workers.base.BaseWorker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImovelOfertadoMatcher extends BaseWorker {

    public void executar(ImovelOfertado imovelOfertado){
        System.out.println("Executando ImovelOfertadoMatcher para o ImovelOfertado("+imovelOfertado.getId()+")");

        List<ImovelDesejado> imoveisDesejados = imovelDesejadoRepository.findDifferentFromIdUsuario(imovelOfertado.getUsuario_id());
        for (ImovelDesejado imovelDesejado : imoveisDesejados) {
            if (imovelOfertado.getArea() >= imovelDesejado.getArea() &&
                imovelOfertado.getTipoImovel() == imovelDesejado.getTipoImovel() &&
                imovelOfertado.getBairro().equals(imovelDesejado.getBairro())){
                Match match = new Match();
                match.setImovelOfertado(imovelOfertado);
                match.setImovelDesejado(imovelDesejado);
                if (matchRepository.findByIdImovelOfertadoEIdImovelDesejado(imovelOfertado.getId(), imovelDesejado.getId()) == null){
                    matchRepository.save(match);
                    System.out.println("Match criado com imóvel desejado de ID("+imovelDesejado.getId()+")");
                }
            }
        }

        System.out.println("Execução finalizada para o ImovelOfertado("+imovelOfertado.getId()+")");
    }
}
