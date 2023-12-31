package br.com.okeaa.apiokeaaproduto.controllers.request.produto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositoRequest {

    @Max(value = 11, message = "Código identificador do depósito")
    @JsonProperty("id")
    public BigDecimal id;

    @Digits(integer = 11, fraction = 4) //	DECIMAL(11,4)
    @Size(message = "Estoque atual da variação no depósito")
    @JsonProperty("estoque")
    public BigDecimal estoque;
}

