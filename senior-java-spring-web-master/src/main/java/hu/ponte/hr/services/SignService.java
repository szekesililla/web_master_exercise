package hu.ponte.hr.services;

import hu.ponte.hr.config.SignConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class SignService {

    SignConfig signConfig;

    public String sign(String fileName) {
        byte[] dataBytes = fileName.getBytes();
        KeyFactory keyFactory;
        Signature signature;
        byte[] digitalSignature = new byte[0];

        try {
            keyFactory = KeyFactory.getInstance("RSA");
            signature = Signature.getInstance("SHA256withRSA");

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(signConfig.getPrimaryKey());
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            signature.initSign(privateKey);
            signature.update(dataBytes);
            digitalSignature = signature.sign();
        } catch (InvalidKeyException | InvalidKeySpecException | SignatureException |
                 NoSuchAlgorithmException invalidKeyException) {
            log.error("Couldn't generate digital sign.", invalidKeyException);
        }

        return Base64.getEncoder().encodeToString(digitalSignature);
    }

}
