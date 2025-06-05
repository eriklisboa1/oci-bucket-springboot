
# OCI Bucket CRUD Demo

Aplicação demonstra operações de CRUD (Create, Read, Update, Delete) em um bucket do **Oracle Cloud Infrastructure (OCI) Object Storage** usando **Java 17+**, **Spring Boot 3.x** e o **SDK oficial da OCI**.

---

## 🗂️ Índice

- [📖 Descrição](#-descrição)
- [⚙️ Pré-requisitos](#-pré-requisitos)
- [🔧 Configuração](#-configuração)
  - [1. Arquivo de Configuração OCI](#1-arquivo-de-configuração-oci)
  - [2. Variáveis de Ambiente (Opcional)](#2-variáveis-de-ambiente-opcional)
  - [3. application.properties](#3-applicationproperties)
- [📥 Instalação](#-instalação)
  - [1. Clonar o Repositório](#1-clonar-o-repositório)
  - [2. Build do Projeto](#2-build-do-projeto)
- [🚀 Execução](#-execução)
  - [1. Executar via IDE](#1-executar-via-ide)
  - [2. Executar JAR Gerado](#2-executar-jar-gerado)
- [📡 Endpoints da API](#-endpoints-da-api)
  - [`POST /upload`](#post-upload)
  - [`GET /listar`](#get-listar)
  - [`GET /download`](#get-download)
  - [`DELETE /delete`](#delete-delete)
- [💻 Exemplos de Requisição](#-exemplos-de-requisição)
  - [– Upload de Arquivo (cURL)](#–-upload-de-arquivo-curl)
  - [– Listar Objetos (cURL)](#–-listar-objetos-curl)
  - [– Download de Arquivo (cURL)](#–-download-de-arquivo-curl)
  - [– Deletar Objeto (cURL)](#–-deletar-objeto-curl)
- [📁 Estrutura de Pastas](#-estrutura-de-pastas)
- [🤝 Contribuição](#-contribuição)
- [📜 Licença](#-licença)

---

## 📖 Descrição

Este projeto demonstra como criar, ler, atualizar e excluir objetos (**CRUD**) em um **bucket** do OCI Object Storage.  
Ele usa:
- **Java 17+** (LTS)
- **Spring Boot 3.x**
- **Oracle Cloud Infrastructure SDK** (dependency `oci-java-sdk-objectstorage`)

A aplicação expõe endpoints REST para interagir com o bucket:
1. **Upload** de arquivos via `multipart/form-data`
2. **Listagem** de todos os objetos no bucket
3. **Download** de um arquivo específico
4. **Exclusão** de um objeto

Use este projeto como ponto de partida para:
- Armazenamento de documentos
- Processamento de arquivos em pipelines de dados
- Integração com microserviços que consomem blobs binários
- Validação rápida da configuração de buckets antes de escalar para produção

---

## ⚙️ Pré-requisitos

Antes de começar, certifique-se de ter instalado e configurado:

| Ferramenta                      | Versão                                |
| -------------------------------- | ------------------------------------- |
| Java Development Kit (JDK)       | **17** ou superior                    |
| Apache Maven                     | **3.6.x** ou superior                 |
| OCI CLI (opcional, mas recomendado) | Última versão disponível              |
| Conta **OCI** com acesso ao Object Storage | —                              |
| IDE Java                         | IntelliJ IDEA / Eclipse / VS Code     |

> 💡 **Dica:** Caso não tenha o [OCI CLI](https://docs.oracle.com/en-us/iaas/Content/API/SDKDocs/cliinstall.htm), você pode usar para validar manualmente operações de bucket (**ls**, **cp**, **rm**) antes de rodar sua aplicação.

---

## 🔧 Configuração

### 1. Arquivo de Configuração OCI

Crie (ou edite) o arquivo de configuração do SDK em:
- **Unix/Mac:** `~/.oci/config`
- **Windows:** `%USERPROFILE%\.oci\config`

Adicione o seguinte conteúdo (substitua `<VALOR>` conforme sua tenancy):

```ini
[DEFAULT]
user=ocid1.user.oc1..<SEU_OCID_DE_USUARIO>
fingerprint=<SEU_FINGERPRINT_DA_CHAVE>
key_file=<CAMINHO_COMPLETO_PARA_SUA_CHAVE_PRIVADA_PEM>
tenancy=ocid1.tenancy.oc1..<SEU_OCID_DE_TENANCY>
region=<SUA_REGIÃO_OCI>  # Ex: sa-vinhedo-1
