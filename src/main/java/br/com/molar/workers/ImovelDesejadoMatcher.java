package br.com.molar.workers;

import br.com.molar.entities.ImovelDesejado;
import br.com.molar.entities.ImovelOfertado;
import br.com.molar.entities.Match;
import br.com.molar.workers.base.BaseWorker;

import java.util.List;

public class ImovelDesejadoMatcher extends BaseWorker {

    public void executar(ImovelDesejado imovelDesejado){
        System.out.println("Executando ImovelDesejadoMatcher para o ImovelDesejado("+imovelDesejado.getId()+")");

        List<ImovelOfertado> imoveisOfertados = imovelOfertadoRepository.findDifferentFromIdUsuario(imovelDesejado.getUsuario_id());
        for (ImovelOfertado imovelOfertado : imoveisOfertados) {
            if (imovelDesejado.getArea() <= imovelOfertado.getArea() &&
                imovelDesejado.getTipoImovel() == imovelOfertado.getTipoImovel() &&
                imovelDesejado.getBairro() == imovelOfertado.getBairro()){
                Match match = new Match();
                match.setImovelDesejado(imovelDesejado);
                match.setImovelOfertado(imovelOfertado);
                if (matchRepository.findByIdImovelOfertadoEIdImovelDesejado(imovelOfertado.getId(), imovelDesejado.getId()) != null){
                    matchRepository.save(match);
                }
                System.out.println("Match criado com imóvel ofertado de ID("+imovelOfertado.getId()+")");
            }
        }

        System.out.println("Execução finalizada para o ImovelDesejado("+imovelDesejado.getId()+")");
    }
}
