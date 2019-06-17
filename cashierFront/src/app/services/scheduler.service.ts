import { Injectable } from '@angular/core';
import { ScheduledTask } from '../model/transaction-management/scheduled-task';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SchedulerService {
  apiURL: string = "/api/schedule";
  user: string;
  constructor(private httpClient: HttpClient) { }

  public getAllTasks() {
    return this.httpClient.get<ScheduledTask[]>(`${this.apiURL}`);
  }
  public addTask(toAdd:ScheduledTask) {
    return this.httpClient.post<ScheduledTask[]>(`${this.apiURL}`,toAdd);
  }

  public deleteTask(toDelete: string) {
    return this.httpClient.delete<ScheduledTask[]>(`${this.apiURL}?id=${toDelete}`);
  }

}
