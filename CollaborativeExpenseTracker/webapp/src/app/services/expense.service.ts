import { Injectable } from "@angular/core";
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from "@angular/common/http";
import { Expense } from "../model/expense.model";
import { Subject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: "root",
})
export class ExpenseService {
  constructor(private http: HttpClient) {}

  private _refreshNeeded$ = new Subject<void>();

  get refreshNeeded$() {
    return this._refreshNeeded$;
  }

  addExpense(
    toUserName: string,
    expenseName: string,
    amount: string,
    type: string,
    fromUserSplitPercentageValue: string,
    toUserSplitPercentageValue: string
  ) {
    console.log("in add expense");
    const expense: Expense = {
      id: "",
      toUserName: toUserName,
      fromUserName: "",
      // toUserName: "",
      name: expenseName,
      amount: amount,
      type: type,
      fromUserSplitPercentageValue: fromUserSplitPercentageValue,
      toUserSplitPercentageValue: toUserSplitPercentageValue
    };
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    console.log(expense);
    return this.http.post<Expense>("http://localhost:8084/api/expense", expense, header)
    .pipe(tap(()=>{
      this._refreshNeeded$.next();
    }));
  }

  public getExpense(){
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    return this.http.get<Expense[]>("http://localhost:8084/api/expense");
  }

  public delete(id:string) {
    console.log("Inside Expense Delete()");
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    console.log("Inside Expense Delete(): Calling Delete REST API");
    return this.http.post( "http://localhost:8084/api/expense/delete/"+ id, header)
    .pipe(tap(()=>{
      this._refreshNeeded$.next();
    }));
  }

  public update(expense:Expense) {
    let id = expense.id;
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    console.log(expense);
    console.log(header);
    console.log("Inside Expense Update(): Calling Delete REST API");
    return this.http.post( "http://localhost:8084/api/expense/put/"+ id, expense, header)
    .pipe(tap(()=>{
      this._refreshNeeded$.next();
    }));
  }
}
