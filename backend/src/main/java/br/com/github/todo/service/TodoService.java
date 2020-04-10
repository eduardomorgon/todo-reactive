package br.com.github.todo.service;

import br.com.github.todo.exception.TodoNotFoundExeception;
import br.com.github.todo.model.Todo;
import br.com.github.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoService {
    
    private final TodoRepository repository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }
    
    public Flux<Todo> findAll() {
        
        return this.repository.findAll();
    }
    
    public Mono<Todo> findBy(String id) {
        
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new TodoNotFoundExeception()));
    }
    
    public Mono<Todo> save(Mono<Todo> todo) {
        
        return todo.flatMap(t -> this.repository.save(t));
    }
    
    public Mono<Todo> edit(String id, Mono<Todo> todo) {
        
        return todo.filter(a -> a.getId().equals(id))
                .flatMap(a -> Mono.zip(Mono.just(a), this.repository.findById(a.getId())))
                .flatMap(zip -> { 
                    zip.getT2().setTask(zip.getT1().getTask());
                    zip.getT2().setLabel(zip.getT1().getLabel());
                    return this.repository.save(zip.getT2());
                });
    }
    
    public Mono<Void> delete(String id) {
        
        return this.findBy(id)
                .flatMap(a -> this.repository.deleteById(a.getId()));
    }
    
}