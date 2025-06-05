# OCI Bucket CRUD Demo

Este projeto demonstra como criar, ler, atualizar e excluir objetos em um bucket do Oracle Cloud Infrastructure (OCI) usando Java Spring Boot e o SDK oficial da OCI.

---

## Índice

- [Descrição](#descrição)
- [Pré-requisitos](#pré-requisitos)
- [Configuração](#configuração)
    - [1. Arquivo de Configuração OCI](#1-arquivo-de-configuração-oci)
    - [2. Variáveis de Ambiente (Opcional)](#2-variáveis-de-ambiente-opcional)
- [Instalação](#instalação)
    - [1. Clonar o Repositório](#1-clonar-o-repositório)
    - [2. Build do Projeto](#2-build-do-projeto)
- [Execução](#execução)
    - [1. Executar via IDE](#1-executar-via-ide)
    - [2. Executar JAR Gerado](#2-executar-jar-gerado)
- [Endpoints da API](#endpoints-da-api)
    - [`POST /upload`](#post-upload)
    - [`GET /listar`](#get-listar)
    - [`GET /download`](#get-download)
    - [`DELETE /delete`](#delete-delete)
- [Exemplos de Requisição](#exemplos-de-requisição)
    - [Upload de Arquivo (cURL)](#upload-de-arquivo-curl)
    - [Listar Objetos (cURL)](#listar-objetos-curl)
    - [Download de Arquivo (cURL)](#download-de-arquivo-curl)
    - [Deletar Objeto (cURL)](#deletar-objeto-curl)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## Descrição

Este projeto configura uma aplicação Spring Boot capaz de interagir com o OCI Object Storage para realizar operações básicas de CRUD (Create, Read, Update, Delete) em objetos dentro de um bucket. A aplicação expõe endpoints REST para:

1. **Upload** de arquivos via multipart/form-data
2. **Listagem** de todos os objetos presentes no bucket
3. **Download** de um arquivo específico do bucket
4. **Exclusão** de um objeto

Internamente, utiliza a classe `ObjectStorageClient` do SDK da Oracle para se comunicar com os serviços de Object Storage.

---

## Pré-requisitos

Antes de iniciar, verifique se você possui:

- **Java 17** ou superior
- **Maven 3.6.x** ou superior
- **Conta e Tenancy OCI** com acesso ao Object Storage
- **OCI CLI** (opcional, mas útil para testar configurações)
- **Uma IDE Java** (IntelliJ IDEA, Eclipse ou VS Code + extensão Java)

---

## Configuração

### 1. Arquivo de Configuração OCI

1. Crie (ou edite) o arquivo de configuração OCI em `~/.oci/config` (Unix/Mac) ou `%USERPROFILE%\.oci\config` (Windows).
2. Adicione as credenciais necessárias:

   ```ini
   [DEFAULT]
   user=ocid1.user.oc1..<SEU_OCID_DE_USUARIO>
   fingerprint=<SEU_FINGERPRINT_DA_CHAVE>
   key_file=<CAMINHO_COMPLETO_PARA_SUA_CHAVE_PRIVADA_PEM>
   tenancy=ocid1.tenancy.oc1..<SEU_OCID_DE_TENANCY>
   region=<SUA_REGIÃO_OCI>  # Ex: sa-vinhedo-1
