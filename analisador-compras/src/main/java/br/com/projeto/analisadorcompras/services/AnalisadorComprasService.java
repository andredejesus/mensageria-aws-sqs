package br.com.projeto.analisadorcompras.services;

import br.com.projeto.analisadorcompras.providers.AwsProvider;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnalisadorComprasService {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.fila.url_solicitacoes_compras_aprovadas}")
    private String urlSolicitacoesComprasAprovadas;

    @Autowired
    private AwsProvider awsProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @SqsListener(value = "${cloud.aws.fila.url_compras_solicitadas}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void recieveMessage(String message) throws Exception{
        log.info("Envio Mensagem - Solicitação de compra aprovada - {}", message);
        envioMensagemSolicitacaoCompraAprovada(message);
    }

    public void envioMensagemSolicitacaoCompraAprovada(String mensagem) throws JsonProcessingException {
        //queueMessagingTemplate.send(urlSolicitacoesComprasAprovadas, MessageBuilder.withPayload(mensagem).build());

        log.info("Mensagem recebida - Solicitação de compra recebida - {}", mensagem);

        SendMessageRequest sendMessageRequest =  new SendMessageRequest().withQueueUrl(urlSolicitacoesComprasAprovadas)
                .withMessageBody(objectMapper.writeValueAsString(mensagem));

        awsProvider.buildAmazonSQSAsync().sendMessage(sendMessageRequest);

    }

}
