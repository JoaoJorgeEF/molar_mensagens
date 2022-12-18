package br.com.molar.consumidor;

import br.com.molar.entities.ImovelDesejado;
import br.com.molar.entities.ImovelOfertado;
import br.com.molar.repository.ImovelDesejadoRepository;
import br.com.molar.repository.ImovelOfertadoRepository;
import br.com.molar.workers.ImovelDesejadoMatcher;
import br.com.molar.workers.ImovelOfertadoMatcher;
import br.com.molar.workers.base.BaseWorker;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class Consumidor {

    @Autowired
    ImovelDesejadoRepository imovelDesejadoRepository;

    @Autowired
    ImovelOfertadoRepository imovelOfertadoRepository;

    @Autowired
    ImovelOfertadoMatcher imovelOfertadoMatcher;

    @Autowired
    ImovelDesejadoMatcher imovelDesejadoMatcher;

    public void consumir() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");
        Connection conexao = connectionFactory.newConnection();

        Channel canal = conexao.createChannel();
        String filaDesejado = "ImovelDesejado";
        String filaOfertado = "ImovelOfertado";
        canal.queueDeclare(filaDesejado, false, false, false, null);
        canal.queueDeclare(filaOfertado, false, false, false, null);

        DeliverCallback callbackDesejado = (consumerTag, delivery) -> {
            Long id = Long.parseLong(new String(delivery.getBody()));
            System.out.println("Mensagem recebida, objeto do tipo("+ filaDesejado +") de ID("+ id +")");
            buscarEntidade(imovelDesejadoRepository, imovelDesejadoMatcher, filaDesejado, id);
        };
        DeliverCallback callbackOfertado = (consumerTag, delivery) -> {
            Long id = Long.parseLong(new String(delivery.getBody()));
            System.out.println("Mensagem recebida, objeto do tipo("+ filaOfertado +") de ID("+ id +")");
            buscarEntidade(imovelOfertadoRepository, imovelOfertadoMatcher, filaOfertado, id);
        };

        canal.basicConsume(filaDesejado, true, callbackDesejado, consumerTag -> {});
        canal.basicConsume(filaOfertado, true, callbackOfertado, consumerTag -> {});
    }

    public static void buscarEntidade(JpaRepository repo, Object matcher, String nomeClasse, long id){
        switch (nomeClasse){
            case "ImovelDesejado":
                ImovelDesejadoRepository imDesRepo = (ImovelDesejadoRepository)repo;
                ImovelDesejado imovelDesejado = imDesRepo.findById(id).orElse(null);
                if (imovelDesejado == null){
                    System.out.println("Não foi encotrado Imóvel Desejado com o ID("+id+") informado");
                    break;
                }
                System.out.println("Imóvel Desejado de ID("+ imovelDesejado.getId() +") encontrado");
                ImovelDesejadoMatcher imovelDesejadoMatcher = (ImovelDesejadoMatcher)matcher;
                imovelDesejadoMatcher.executar(imovelDesejado);
                break;
            case "ImovelOfertado":
                ImovelOfertadoRepository imOfRepo = (ImovelOfertadoRepository)repo;
                ImovelOfertado imovelOfertado = imOfRepo.findById(id).orElse(null);
                if (imovelOfertado == null){
                    System.out.println("Não foi encotrado Imóvel Ofertado com o ID("+id+") informado");
                    break;
                }
                System.out.println("Imóvel Ofertado de ID("+ imovelOfertado.getId() +") encontrado");
                ImovelOfertadoMatcher imovelOfertadoMatcher = (ImovelOfertadoMatcher)matcher;
                imovelOfertadoMatcher.executar(imovelOfertado);
                break;
        }
    }
}
