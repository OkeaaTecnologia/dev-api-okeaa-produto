package br.com.okeaa.apiokeaaproduto.controllers.request.produtoFornecedor;

import br.com.okeaa.apiokeaaproduto.exceptions.produtoFornecedor.ErroResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetornoRequest {

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("produtosfornecedores")
    public ArrayList<Produtosfornecedore> produtosfornecedores;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("erros")
    public List<ErroResponse> erros;

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Produtosfornecedore {

        @JsonProperty("produtoFornecedor")
        public ProdutoFornecedorRequest produtoFornecedor;
    }
}
