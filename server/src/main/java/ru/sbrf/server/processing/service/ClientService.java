package ru.sbrf.server.processing.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sbrf.server.processing.AccountDTO;
import ru.sbrf.server.processing.CardDTO;
import ru.sbrf.server.processing.ClientDTO;
import ru.sbrf.server.processing.entity.Account;
import ru.sbrf.server.processing.entity.Card;
import ru.sbrf.server.processing.entity.Client;
import ru.sbrf.server.processing.exception.ClientNotFoundException;
import ru.sbrf.server.processing.repository.ClientCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientCrudRepository clientCrudRepository;

    public ClientDTO getClient(Long id) {
        Client client = clientCrudRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        Set<Account> accountSet = client.getAccounts();
        List<AccountDTO> accountDTOSet = new ArrayList<>();

        for (Account account : accountSet) {

            Set<Card> cardSet = account.getCards();
            List<CardDTO> cardDTOSet = new ArrayList<>();
            for (Card card : cardSet) {
                cardDTOSet.add(new CardDTO(
                    card.getPinCode(),
                    card.getCardNum(),
                    card.getExpireDate(),
                    card.getCvcCode()
                ));
            }

            accountDTOSet.add(new AccountDTO(
                    account.getId().intValue(),
                    account.getAccountNum(),
                    account.getIsoCode(),
                    account.getBalance(),
                    cardDTOSet
                    ));
        }

        return new ClientDTO(
                //client.getId().intValue(),    // Возможно Id понадобится
                client.getFirstName(),
                client.getLastName(),
                accountDTOSet);
    }

    public List<ClientDTO> getAllClients() {
        Iterable<Client> clientIterable = clientCrudRepository.findAll();
        List<ClientDTO> clients = new ArrayList<>();

        clientIterable.forEach(
                client -> {
                    Set<Account> accountSet = client.getAccounts();
                    List<AccountDTO> accountDTOSet = new ArrayList<>();

                    for (Account account : accountSet) {

                        Set<Card> cardSet = account.getCards();
                        List<CardDTO> cardDTOSet = new ArrayList<>();
                        for (Card card : cardSet) {
                            cardDTOSet.add(new CardDTO(
                                    card.getPinCode(),
                                    card.getCardNum(),
                                    card.getExpireDate(),
                                    card.getCvcCode()
                            ));
                        }

                        accountDTOSet.add(new AccountDTO(
                                account.getId().intValue(),
                                account.getAccountNum(),
                                account.getIsoCode(),
                                account.getBalance(),
                                cardDTOSet
                        ));
                    }

                    clients.add(new ClientDTO(
                            //client.getId().intValue(),    // Возможно Id понадобится
                            client.getFirstName(),
                            client.getLastName(),
                            accountDTOSet));
                }
        );
        return clients;
    }
}
