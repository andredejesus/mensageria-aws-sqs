package br.com.projeto.comprasolicitada.providers;

import com.amazonaws.services.sqs.AmazonSQSAsync;

public interface AwsProvider {
    AmazonSQSAsync buildAmazonSQSAsync();
}
