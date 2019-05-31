import { Injectable } from '@angular/core';
import { Subject, Observable } from "rxjs";
import { Status } from '../model/status';
@Injectable({
  providedIn: 'root'
})
export class StatusServiceService {

  constructor() { }
  private subject = new Subject<any>();

  sendMessage(message: Status) {
    this.subject.next(message);
  }
  getMessage(): Observable<Status> {
    return this.subject.asObservable();
  }
}
