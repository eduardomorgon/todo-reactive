import { Component, OnInit, Input } from '@angular/core';
import { Todo } from '../../model/todo.model';
import { TodoService } from '../../service/todo.service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-todo-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  @Input() todo:Todo;
  @Input() todos: Observable<Todo[]>;
  constructor(private service:TodoService) { }

  ngOnInit(): void { }

  loadTodo(todo:Todo) {
    
    this.todo = todo;
  }

  salvar(todo: Todo) {

    this.service.salvar(todo)
      .subscribe(
        (res) => { 
          this.todos.pipe(
            map(todos =>  { 
              if(todo.id) {
                let index = todos.findIndex(t => t.id === todo.id);
                todos.splice(index, 1, res.body);
              }else{
                todos.push(res.body);
              }
              return todos;
            }), 
            map(todos => of(todos))
          ).subscribe(observable => this.todos = observable);
        },
        (error) => console.log(error),
        (() => this.loadTodo(new Todo()))
      );
  }
  
}
