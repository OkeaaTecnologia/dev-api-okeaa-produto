package br.com.okeaa.apiokeaaproduto.repositories.listaPreco;

import br.com.okeaa.apiokeaaproduto.controllers.request.listaPreco.ListaPrecoRequest;
import br.com.okeaa.apiokeaaproduto.controllers.response.listaPreco.ListaPrecoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListaPrecoRequestRepository extends JpaRepository<ListaPrecoRequest, Long> {

    Optional<ListaPrecoRequest> findByIdLista(String idLista);

}


