package com.bucketjava.demo.config;

import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class    OciConfig {

    @Value("${oci.config.path}")
    private String ociConfigPath;

    @Value("${oci.config.profile}")
    private String ociConfigProfile;


    private AuthenticationDetailsProvider buildAuthProvider() throws IOException {
        return new ConfigFileAuthenticationDetailsProvider(ociConfigPath, ociConfigProfile);
    }

    @Bean
    public ObjectStorageClient objectStorageClient() throws IOException {
        return new ObjectStorageClient(buildAuthProvider());
    }
}
