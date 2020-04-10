package br.com.github.todo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "todos")
public class Todo {
    
    @Id
    private String id;
    private String task;
    private String label;
}