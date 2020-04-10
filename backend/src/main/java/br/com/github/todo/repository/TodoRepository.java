package br.com.github.todo.repository;

import br.com.github.todo.model.Todo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, String>{
    
}
