package ru.sbrf.server.processing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sbrf.server.processing.entity.Card;

import java.util.Optional;

@Repository
public interface CardCrudRepository extends CrudRepository<Card, Long> {

    Optional<Card> findByCardNum(String cardNum);
}
