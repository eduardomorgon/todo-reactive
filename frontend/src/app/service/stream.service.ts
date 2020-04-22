import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { startWith } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export abstract class StreamService <T> {

  private obj: T;

  constructor() { }

  getDataStream(): Observable<T> {

    return new Observable<any>(observable => {
      let url = this.url();
      this.obj = this.inciarObj();
      const eventSource = new EventSource(url);

      eventSource.addEventListener('message', (event: any) => {
        console.log(`recebendo os dados stream: ${event.data}`);
        this.insereDadoNoObj(this.obj, event.data != null ? JSON.parse(event.data) : event.data)
        observable.next(this.obj);
      });
      eventSource.addEventListener('error', (error) => {
        //readyState === 0 - significa que o fluxo de dados foi finalizado.
        if(eventSource.readyState === 0) {
          console.log(`o fluxo de dados foi finalizado.`);
          eventSource.close();
          observable.complete();
        }else{
          observable.error(`fluxo de dados com erro: ${error}`);
        }
      })
    }).pipe(startWith([]));
  }

  protected abstract url():string;

  protected abstract inciarObj():T;

  protected abstract insereDadoNoObj(obj:T, json:any):void;

}