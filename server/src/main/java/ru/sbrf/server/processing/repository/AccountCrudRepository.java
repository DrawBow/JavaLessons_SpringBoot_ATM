package ru.sbrf.server.processing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sbrf.server.processing.entity.Account;

@Repository
public interface AccountCrudRepository extends CrudRepository<Account, Long> {
}
