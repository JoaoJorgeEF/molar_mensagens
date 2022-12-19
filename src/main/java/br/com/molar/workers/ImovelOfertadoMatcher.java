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
        int hasMatchesCounter = 0;
        for (ImovelDesejado imovelDesejado : imoveisDesejados) {
            Match matchAntigo = matchRepository.findByIdImovelOfertadoEIdImovelDesejado(imovelOfertado.getId(), imovelDesejado.getId());
            if (temMatch(imovelOfertado, imovelDesejado)){

                Match match = new Match();
                match.setImovelOfertado(imovelOfertado);
                match.setImovelDesejado(imovelDesejado);

                if (matchAntigo == null){
                    matchRepository.save(match);
                    imovelDesejado.setHasMatches(true);
                    imovelDesejadoRepository.save(imovelDesejado);
                    System.out.println("Match criado com imóvel desejado de ID("+imovelDesejado.getId()+")");
                    hasMatchesCounter++;
                }
            } else if (matchAntigo != null){
                matchRepository.deleteById(matchAntigo.getId());
            }
        }
        if (hasMatchesCounter > 0) imovelOfertado.setHasMatches(true);
        else imovelOfertado.setHasMatches(false);

        imovelOfertadoRepository.save(imovelOfertado);
        System.out.println("Execução finalizada para o ImovelOfertado("+imovelOfertado.getId()+")");
    }
}
