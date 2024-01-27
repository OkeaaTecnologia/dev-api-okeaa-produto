package br.com.okeaa.apiokeaaproduto.controllers;

import br.com.okeaa.apiokeaaproduto.controllers.request.deposito.JsonRequest;
import br.com.okeaa.apiokeaaproduto.controllers.response.deposito.JsonResponse;
import br.com.okeaa.apiokeaaproduto.exceptions.deposito.*;
import br.com.okeaa.apiokeaaproduto.service.deposito.DepositoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")        //Padrão para os métodos /api/...
@Api(value = "API REST DEPOSITOS")    //Swagger
@CrossOrigin(origins = "*")        // Liberar os dominios da API
public class DepositoController {

    public static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    public DepositoService depositoService;


    /**
     * GET "BUSCA LISTA DE DEPOSITOS".
     */
    @GetMapping("/depositos")
    @ApiOperation(value = "Retorna uma lista de depositos")
    public ResponseEntity<JsonResponse> getAllDeposit() {
        try {
            JsonResponse request = depositoService.getAllDeposit();

            if (request.retorno.depositos == null && request.retorno.erros == null) {
                throw new DepositoListaException("Não foi possível localizar a lista de depositos");
            }

            logger.info("GET: " + request);

            return ResponseEntity.status(HttpStatus.OK).body(request);
        } catch (Exception e) {
            throw new ApiDepositoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * GET "BUSCA DEPOSITO EXISTENTE PELO IDDEPOSITO".
     */
    @GetMapping("/deposito/{idDeposito}")
    @ApiOperation(value = "Retorna um deposito pelo idDeposito")
    public ResponseEntity<JsonResponse> getDepositById(@PathVariable String idDeposito) {
        try {
            JsonResponse request = depositoService.getDepositById(idDeposito);

            if (request.retorno.depositos == null && request.retorno.erros == null) {
                throw new DepositoIdDepositoException("Contato com o número de CPF/CNPJ: " + idDeposito + " não encontrado.");
            }

            logger.info("GET ID: " + request);

            return ResponseEntity.status(HttpStatus.OK).body(request);
        } catch (Exception e) {
            throw new ApiDepositoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * POST "CADASTRAR UM NOVO DEPOSITO UTILIZANDO XML".
     */
    @PostMapping(path = "/cadastrardeposito", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastrar um novo deposito")
    public ResponseEntity<String> createDeposit(@RequestBody @Valid String xmlDeposito) {
        try {
            String request = depositoService.createDeposit(xmlDeposito).getBody();

            logger.info("POST: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiDepositoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * PUT "ATUALIZAR UM DEPOSITO EXISTENTE UTILIZANDO XML".
     */
    @PutMapping(path = "/atualizardeposito/{idDeposito}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Atualizar um deposito existente")
    public ResponseEntity<String> updateDeposit(@RequestBody @Valid String xmlDeposito, @PathVariable String idDeposito) {
        try {
            String request = depositoService.updateDeposit(xmlDeposito, idDeposito).getBody();

            logger.info("UPDATE: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiDepositoException("Houve algum erro sistemico, tente novamente", e);
        }
    }
}