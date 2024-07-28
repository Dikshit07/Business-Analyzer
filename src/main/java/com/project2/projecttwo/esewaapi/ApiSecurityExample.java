package com.project2.projecttwo.esewaapi;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class ApiSecurityExample {
    public String performpayment(String total_amount,String transaction_uuid ) throws InvalidKeyException, NoSuchAlgorithmException {

        String secret = "8gBm/:&EnhH.1/q";
        // String transaction_uuid = UUID.randomUUID().toString();
        String product_code = "EPAYTEST";

        String message = "total_amount=" + total_amount +
                ",transaction_uuid=" + transaction_uuid +
                ",product_code=" + product_code;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        String hashone = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));

        System.out.println("Hash :" + hashone);
        System.out.println("ID :"+transaction_uuid);

        return hashone;
    }

}
