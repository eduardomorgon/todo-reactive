package br.com.github.todo.controller;

import br.com.github.todo.model.Todo;
import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import br.com.github.todo.service.TodoService;

@RestController
@RequestMapping("todos")
public class TodoRestController {
    
    private final TodoService service;

    public TodoRestController(TodoService service) {
        this.service = service;
    }
    
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Todo> findAll() {
        
        return this.service.findAll().delayElements(Duration.ofMillis(200)).log();
    }
    
    @GetMapping("{id}")
    public Mono<ResponseEntity<Todo>> findBy(@PathVariable String id) {
        
        return this.service.findBy(id)
                .map(agenda -> ResponseEntity.ok(agenda))
                .onErrorReturn(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Mono<ResponseEntity<Todo>> save(@RequestBody Mono<Todo> todo, UriComponentsBuilder uriBuilder) {
        
        return this.service.save(todo).map(
                 entity -> ResponseEntity.created(
                            uriBuilder.path("/todos/{id}")
                                .buildAndExpand(entity.getId())
                                .toUri())
                         .body(entity));
    }
    
    @PutMapping("{id}")
    public Mono<ResponseEntity<Todo>> edit(@PathVariable String id, @RequestBody Mono<Todo> todo) {
        
        return this.service.edit(id, todo)
                .map(a -> ResponseEntity.ok(a))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        
        return this.service.delete(id)
                .thenReturn(ResponseEntity.noContent().<Void>build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}