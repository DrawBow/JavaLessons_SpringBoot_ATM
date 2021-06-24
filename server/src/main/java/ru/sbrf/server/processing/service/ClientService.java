package ru.sbrf.server.processing.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sbrf.server.processing.AccountDTO;
import ru.sbrf.server.processing.CardDTO;
import ru.sbrf.server.processing.ClientDTO;
import ru.sbrf.server.processing.entity.Account;
import ru.sbrf.server.processing.entity.Card;
import ru.sbrf.server.processing.entity.Client;
import ru.sbrf.server.processing.exception.AccountNotFoundException;
import ru.sbrf.server.processing.exception.CardNotFoundException;
import ru.sbrf.server.processing.exception.ClientNotFoundException;
import ru.sbrf.server.processing.repository.AccountCrudRepository;
import ru.sbrf.server.processing.repository.CardCrudRepository;
import ru.sbrf.server.processing.repository.ClientCrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientCrudRepository clientCrudRepository;
    private AccountCrudRepository accountCrudRepository;
    private CardCrudRepository cardCrudRepository;

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

    // Возвращает клиента по номеру карты
    public ClientDTO getClientByCardNum(String cardNum) {
        Card card = cardCrudRepository.findByCardNum(cardNum).orElseThrow(CardNotFoundException::new);
        Account account = card.getAccount_id(); // Надо ли проверять, что объект существует?
        Client client = account.getClient_id();

        List<CardDTO> cardDTOSet = new ArrayList<>();
        cardDTOSet.add(new CardDTO(
                card.getPinCode(),
                card.getCardNum(),
                card.getExpireDate(),
                card.getCvcCode()
        ));

        List<AccountDTO> accountDTOSet = new ArrayList<>();
        accountDTOSet.add(new AccountDTO(
                account.getId().intValue(),
                account.getAccountNum(),
                account.getIsoCode(),
                account.getBalance(),
                cardDTOSet
        ));

        return new ClientDTO(
                client.getFirstName(),
                client.getLastName(),
                accountDTOSet);
    }
}
