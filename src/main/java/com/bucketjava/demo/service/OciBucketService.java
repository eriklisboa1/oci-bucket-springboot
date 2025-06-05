package com.bucketjava.demo.service;


import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.DeleteObjectResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OciBucketService {

    private final ObjectStorageClient client;

    @Value("${oci.namespace}")
    private String namespace;

    @Value("${oci.bucket}")
    private String bucketName;

    public OciBucketService(ObjectStorageClient client) {
        this.client = client;
    }

    /**
     * Lista todos os objetos que começam com um prefixo (ex: "entrada/")
     */
    public List<String> listarArquivos(String prefixo) {
        ListObjectsResponse response = client.listObjects(
                ListObjectsRequest.builder()
                        .namespaceName(namespace)
                        .bucketName(bucketName)
                        .prefix(prefixo)
                        .build()
        );
        return response.getListObjects().getObjects()
                .stream()
                .map(obj -> obj.getName())
                .collect(Collectors.toList());
    }


    public boolean deletarArquivo(String nomeObjeto) {
        DeleteObjectResponse resp = client.deleteObject(
                DeleteObjectRequest.builder()
                        .namespaceName(namespace)
                        .bucketName(bucketName)
                        .objectName(nomeObjeto)
                        .build()
        );
        return resp.get__httpStatusCode__() / 100 == 2;
    }

    public InputStream baixarArquivo(String nomeObjeto) {
        GetObjectResponse resp = client.getObject(
                GetObjectRequest.builder()
                        .namespaceName(namespace)
                        .bucketName(bucketName)
                        .objectName(nomeObjeto)
                        .build()
        );
        return resp.getInputStream();
    }


    public void enviarArquivo(String nomeObjeto, MultipartFile file) throws Exception {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .namespaceName(namespace)
                .bucketName(bucketName)
                .objectName(nomeObjeto)
                .contentLength(file.getSize())
                .putObjectBody(file.getInputStream())
                .build();

        client.putObject(putRequest);
    }
}
