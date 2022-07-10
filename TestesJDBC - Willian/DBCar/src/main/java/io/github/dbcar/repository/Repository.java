package io.github.dbcar.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ID_TYPE, OBJECT> {
  List<OBJECT> findAll();

  OBJECT findById(ID_TYPE id);

  Optional<OBJECT> findOne(ID_TYPE id);

  void update(ID_TYPE id, OBJECT object);

  void delete(ID_TYPE id);
}
