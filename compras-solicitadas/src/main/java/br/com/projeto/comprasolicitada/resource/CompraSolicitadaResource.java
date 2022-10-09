package br.com.projeto.comprasolicitada.resource;

import br.com.projeto.comprasolicitada.config.AwsSQSConfig;
import br.com.projeto.comprasolicitada.providers.AwsProvider;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/compraSolicitada")
public class CompraSolicitadaResource {

    @Autowired
    private AwsSQSConfig awsSQSConfig;
    @Autowired
    private AwsProvider awsProvider;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping
    public ResponseEntity<SendMessageRequest> compraSolicitada(@RequestBody Map<String, String> message) throws JsonProcessingException {


        log.info("Mensagem recebida - Solicitação de compra recebida - {}", message);

        SendMessageRequest sendMessageRequest =  new SendMessageRequest().withQueueUrl(awsSQSConfig.getUrlFilaComprasSolicitadas())
                .withMessageBody(objectMapper.writeValueAsString(message));

        awsProvider.buildAmazonSQSAsync().sendMessage(sendMessageRequest);

        return ResponseEntity.ok(sendMessageRequest);
    }

//    @PostMapping("/fila-fifo")
//    public ResponseEntity<String> solicitacaoCompraCartaoCreditoFilaFifo(@RequestBody Map<String, String> message) throws JsonProcessingException {
//
//
//        log.info("Message do SQS Queue - Solicitação de compra recebida - {}", message);
//
//        SendMessageRequest sendMessageRequest = new SendMessageRequest()
//                .withQueueUrl(awsSQSConfig.getUriComprasSolicitadas())
//                .withMessageBody(objectMapper.writeValueAsString(message))
//                .withMessageGroupId("teste");
//
//
//        return ResponseEntity.ok("Solicitação de compra enviada com sucesso.");
//    }







}
