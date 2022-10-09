package br.com.projeto.analisadorcompras.providers;

import com.amazonaws.services.sqs.AmazonSQSAsync;

public interface AwsProvider {
    AmazonSQSAsync buildAmazonSQSAsync();
}
