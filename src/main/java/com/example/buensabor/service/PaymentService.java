package com.example.buensabor.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.buensabor.entity.Order;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import jakarta.annotation.PostConstruct;

@Service

public class PaymentService {
    
    
    //@Value("${mercadopago.access.token}")
    //private String mercadoPagoAccessToken;

    /*@PostConstruct
    public void init() {
        //MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
    }*/

    public Preference createPreference(Order order) throws MPException, MPApiException {

        // Crear ítem con el total de la orden
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title("Pago de orden N° " + order.getId())
                .quantity(1)
                .currencyId("ARS") // o la moneda que uses
                .unitPrice(BigDecimal.valueOf(order.getTotal()))
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        // Back URLs para redireccionar después del pago
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://tusitio.com/success")
                .failure("https://tusitio.com/failure")
                .pending("https://tusitio.com/pending")
                .build();

        // Armar preference request
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .externalReference(String.valueOf(order.getId()))
                .build();

        // Crear preferencia vía API
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference;
    }
}

