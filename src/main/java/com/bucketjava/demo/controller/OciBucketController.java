package com.bucketjava.demo.controller;


import com.bucketjava.demo.service.OciBucketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/objects")
public class OciBucketController {

    private final OciBucketService bucketService;

    public OciBucketController(OciBucketService bucketService) {
        this.bucketService = bucketService;
    }



    @GetMapping
    public ResponseEntity<List<String>> listar(@RequestParam(name = "prefixo", defaultValue = "") String prefixo) {
        List<String> arquivos = bucketService.listarArquivos(prefixo);
        return ResponseEntity.ok(arquivos);
    }


    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam("name") String nomeObjeto) {
        boolean ok = bucketService.deletarArquivo(nomeObjeto);
        return ok
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("name") String nomeObjeto) throws Exception {
        InputStream in = bucketService.baixarArquivo(nomeObjeto);
        byte[] conteudo = in.readAllBytes();

        // Define headers para forçar download
        String fileNameEncoded = URLEncoder.encode(nomeObjeto.substring(nomeObjeto.lastIndexOf("/") + 1), StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(conteudo.length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNameEncoded + "\"");

        return new ResponseEntity<>(conteudo, headers, HttpStatus.OK);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("name") String nomeObjeto,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            bucketService.enviarArquivo(nomeObjeto, file);
            return ResponseEntity.ok("Arquivo enviado: " + nomeObjeto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha no upload: " + e.getMessage());
        }
    }
}
