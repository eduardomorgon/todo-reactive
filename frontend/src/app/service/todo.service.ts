import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Todo } from '../model/todo.model';
import { StreamService } from './stream.service';

@Injectable({
  providedIn: 'root'
})
export class TodoService extends StreamService<Array<Todo>> {
  
  private api:string = "http://localhost:8080/todos";
  
  constructor(private http: HttpClient){
    super();
  }
  
  buscarPor(id:string) {

    return this.http.get(`${this.api}/${id}`);
  }

  excluir(id: string) {

    return this.http.delete(`${this.api}/${id}`);
  }

  salvar(todo:Todo) {

    if(todo.id) {
      return this.http.put(`${this.api}/${todo.id}`, todo, {observe: 'response'});
    }else{
      delete todo.id;
      return this.http.post(`${this.api}`, todo, {observe: 'response'});
    }
  }

  protected insereDadoNoObj(obj: Todo[], json: any): void {

    obj.push(json);
  }
  
  protected url(): string {

    return this.api;
  }

  protected inciarObj(): Todo[] {
    
    return [];
  }

}
