package br.com.molar.consumidor;

import br.com.molar.entities.ImovelDesejado;
import br.com.molar.entities.ImovelOfertado;
import br.com.molar.repository.ImovelDesejadoRepository;
import br.com.molar.repository.ImovelOfertadoRepository;
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

    public void consumir(String nomeFila) throws Exception {
        //criando a fabrica de conexoes e criando uma conexao
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");
        Connection conexao = connectionFactory.newConnection();

        //criando um canal e declarando uma fila
        Channel canal = conexao.createChannel();
        canal.queueDeclare(nomeFila, false, false, false, null);

        //Definindo a funcao callback
        DeliverCallback callback = (consumerTag, delivery) -> {
            Long id = Long.parseLong(new String(delivery.getBody()));
            System.out.println("Mensagem recebida, objeto do tipo("+ nomeFila +") de ID("+ id +")");
            buscarEntidade(nomeFila == "ImovelDesejado" ? imovelDesejadoRepository : imovelOfertadoRepository, nomeFila, id);
        };

        //Consome da fila
        canal.basicConsume(nomeFila, true, callback, consumerTag -> {});
    }

    public static void buscarEntidade(JpaRepository repo, String nomeClasse, long id){
        switch (nomeClasse){
            case "ImovelDesejado":
                ImovelDesejadoRepository imDesRepo = (ImovelDesejadoRepository)repo;
                ImovelDesejado imovelDesejado = imDesRepo.findById(id).orElse(null);
                System.out.println(imovelDesejado.toString());
                break;
            case "ImovelOfertado":
                ImovelOfertadoRepository imOfRepo = (ImovelOfertadoRepository)repo;
                ImovelOfertado imovelOfertado = imOfRepo.findById(id).orElse(null);
                System.out.println(imovelOfertado.getBairro());
                break;
        }
    }
}
