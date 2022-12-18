package br.com.molar.workers;

import br.com.molar.entities.ImovelDesejado;
import br.com.molar.entities.ImovelOfertado;
import br.com.molar.entities.Match;
import br.com.molar.workers.base.BaseWorker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImovelDesejadoMatcher extends BaseWorker {

    public void executar(ImovelDesejado imovelDesejado){
        System.out.println("Executando ImovelDesejadoMatcher para o ImovelDesejado("+imovelDesejado.getId()+")");

        List<ImovelOfertado> imoveisOfertados = imovelOfertadoRepository.findDifferentFromIdUsuario(imovelDesejado.getUsuario_id());
        int hasMatchesCounter = 0;
        for (ImovelOfertado imovelOfertado : imoveisOfertados) {
            if (temMatch(imovelOfertado, imovelDesejado)){

                Match match = new Match();
                match.setImovelDesejado(imovelDesejado);
                match.setImovelOfertado(imovelOfertado);

                if (matchRepository.findByIdImovelOfertadoEIdImovelDesejado(imovelOfertado.getId(), imovelDesejado.getId()) == null){
                    matchRepository.save(match);
                    imovelOfertado.setHasMatches(true);
                    imovelOfertadoRepository.save(imovelOfertado);
                    System.out.println("Match criado com imóvel ofertado de ID("+imovelOfertado.getId()+")");
                    hasMatchesCounter++;
                }

            }
        }
        if (hasMatchesCounter > 0) imovelDesejado.setHasMatches(true);

        imovelDesejadoRepository.save(imovelDesejado);
        System.out.println("Execução finalizada para o ImovelDesejado("+imovelDesejado.getId()+")");
    }
}
