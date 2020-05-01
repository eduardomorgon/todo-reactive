import { Component, OnInit, Input } from '@angular/core';
import { TodoService } from '../../service/todo.service';
import { Todo } from '../../model/todo.model';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
@Component({
  selector: 'app-todo-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent implements OnInit {

  todo: Todo;
  todos: Observable<Todo[]>;
  
  constructor(private service:TodoService) { }

  ngOnInit(): void {

    this.todo = new Todo();
    this.load();
  }

  load(): void {

    this.service.getDataStream()
    .subscribe(todos => this.todos = of(todos));
  }

  excluir(id: string) {

    this.service.excluir(id)
      .subscribe(
        () => { 
          this.todos.pipe(
            map(todos => todos.filter(todo => todo.id !== id)), 
            map(todos => of(todos))
          ).subscribe(observable => this.todos = observable);
        }, 
        erro => {
              console.log('erro')
        }
      );
  }

  editar(todo:Todo) {

    let body: Todo = new Todo() ;
    Object.assign(body, todo);
    this.todo = body;
  }

}
