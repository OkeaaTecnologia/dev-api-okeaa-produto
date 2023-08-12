package br.com.okeaa.apiokeaaproduto.controllers;

import br.com.okeaa.apiokeaaproduto.controllers.request.produto.JsonRequest;
import br.com.okeaa.apiokeaaproduto.controllers.response.produto.JsonResponse;
import br.com.okeaa.apiokeaaproduto.exceptions.produto.*;
import br.com.okeaa.apiokeaaproduto.service.produto.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/v1")        //Padrão para os métodos /api/...
@Api(value = "API REST PRODUTOS")    //Swagger
@CrossOrigin(origins = "*")        // Liberar os dominios da API
public class ProdutoController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProdutoService produtoService;
    private String codigo;

    /**
     * GET "BUSCAR LISTA DE PRODUTOS".
     */
    @GetMapping("/produtos")
    @ApiOperation(value = "Retorna uma lista de produtos")
    public ResponseEntity<JsonResponse> getAllProducts() {
        try {
            JsonResponse request = produtoService.getAllProducts();

            if (request.retorno.produtos == null && request.retorno.erros == null) {
                throw new ProdutoListaException("Não foi possível localizar a lista de produtos");
            }

            System.out.println("GET: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiProdutoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * GET "BUSCAR UM PRODUTO PELO CÒDIGO (SKU)".
     */
    @GetMapping("/produto/{codigo}")
    @ApiOperation(value = "Retorna um produto pelo código")
    public ResponseEntity<JsonResponse> getProductByCode(@PathVariable String codigo) {
        try {
            JsonResponse request = produtoService.getProductByCode(codigo);

            if (request.retorno.produtos == null && request.retorno.erros == null) {
                throw new ProdutoCodigoException("Produto com código " + codigo + " não localizado.");
            }

            System.out.println("GET ID: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiProdutoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * GET "PRODUTO UTILIZANDO O CODIGOFABRICANTE E IDFABRICANTE".
     */
    @GetMapping("/produto/{codigoFabricante}/{idFabricante}")
    @ApiOperation(value = "Retorna um produto pelo código e idFabricante")
    public ResponseEntity<JsonResponse> getProductByCodeSupplier(@PathVariable String codigoFabricante, @PathVariable String idFabricante) {
        JsonResponse request = produtoService.getProductByCodeSupplier(codigoFabricante, idFabricante);
        try {

            if (request.retorno.produtos == null && request.retorno.erros == null) {
                throw new ProdutoCodigoFornecedorException("Produto com código: " + codigoFabricante + " e idFabricante: " + idFabricante + " não localizado.");
            }

            System.out.println("GET ID+FAB: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiProdutoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * DELETE PROUTO PELO CÓDIGO (SKU).
     */
    @DeleteMapping("/produto/{codigo}")
    @ApiOperation(value = "Deletar um produto pelo código")
    public ResponseEntity<Object> deleteProductByCode(@PathVariable String codigo) {
        try {
            JsonResponse request = produtoService.getProductByCode(codigo);

            if (request.retorno.produtos == null && request.retorno.erros == null) {
                throw new ProdutoExclusaoException("Cadastro não deletado, revise o código do produto e tente novamente!");
            }
            produtoService.deleteProductByCode(codigo);

            System.out.println("Codigo deletado = " + codigo);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiProdutoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * POST "CADASTRAR UM NOVO PRODUTO UTILIZANDO XML".
     */
    @PostMapping(path = "/cadastrarproduto", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastrar um novo produto")
    public ResponseEntity<JsonRequest> createProduct(@RequestBody @Valid String xmlProdutos) {
        try {
            JsonRequest request = produtoService.createProduct(xmlProdutos);

            if (request.retorno.produtos == null && request.retorno.erros == null) {
                throw new ProdutoCadastroException("Cadastro não efetuado, revise os campos e tente novamente!");
            }

            System.out.println("POST: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiProdutoException("Houve algum erro sistemico, tente novamente", e);
        }
    }

    /**
     * POST "ATUALIZAR UM PRODUTO EXISTENTE PELO CODIGO UTILIZANDO XML".
     */
    @PostMapping(path = "/atualizarproduto/{codigo}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Atualizar um produto existente")
    public ResponseEntity<JsonRequest> updateProduct(@RequestBody @Valid String xml, @PathVariable String codigo) {
        try {
            JsonRequest request = produtoService.updateProduct(xml, codigo);

            if (request.retorno.produtos == null && request.retorno.erros == null) {
                throw new ProdutoAtualizarException("Não foi possível atualizar a categoria pelo código: " + codigo);
            }

            System.out.println("UPDATE: " + request);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            throw new ApiProdutoException("Houve algum erro sistemico, tente novamente", e);
        }
    }
}