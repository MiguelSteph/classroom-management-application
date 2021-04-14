package com.classmanagement.authorizationserver.endpoints;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@FrameworkEndpoint
@AllArgsConstructor
public class JwkSetEndpoint {

    private final KeyPair keyPair;

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey rsaKey = new RSAKey.Builder(publicKey).build();
        return new JWKSet(rsaKey).toJSONObject();
    }

}
