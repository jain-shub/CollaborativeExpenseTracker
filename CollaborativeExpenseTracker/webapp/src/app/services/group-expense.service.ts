import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { GroupExpense } from "app/model/group-expense.model";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Group } from 'app/model/group.model';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: "root",
})
export class GroupExpenseService {
  constructor(private http: HttpClient) {}

  private _refreshNeeded$ = new Subject<void>();

  get refreshNeeded$() {
    return this._refreshNeeded$;
  }

  addGroupExpense(
    name: string,
    sourceGroup: string,
    amount: number,
    dividePercentage: any,
    paidBy: string,
    createdBy: string
  ): Observable<GroupExpense> {
    const groupExp: GroupExpense = {
      id:"",
      name: name,
      sourceGroup: sourceGroup,
      amount: amount,
      dividePercentage: dividePercentage,
      paidBy: paidBy,
      createdBy: createdBy,
    };
    return this.http.post<GroupExpense>(
      "http://localhost:8084/api/grpExpense",
      groupExp
    ).pipe(tap(()=>{
      this._refreshNeeded$.next();
    }));
  }

  public getGroupExpenses(){
    const token = localStorage.getItem('token');
    const groupId = localStorage.getItem('currentGroupId');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    return this.http.get<GroupExpense[]>("http://localhost:8084/api/grpExpense");
  }

  public delete(id:string) {
    console.log("Inside Expense Delete()");
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    console.log("Inside Expense Delete(): Calling Delete REST API");
    return this.http.post( "http://localhost:8084/api/grpExpense/delete/"+ id, header);
  }

  public update(expense:GroupExpense) {
    let id = expense.id;
    const groupId = localStorage.getItem('currentGroupId');
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    console.log(expense);
    console.log(header);
    console.log("Inside Expense Update(): Calling Delete REST API");
    return this.http.post( "http://localhost:8084/api/grpExpense"+id, header);
  }

  public settle(expense:GroupExpense) {
    let id = expense.id;
    const groupId = localStorage.getItem('currentGroupId');
    const token = localStorage.getItem('token');
    var header = {
      headers: new HttpHeaders().set('Authorization',  `Bearer `+ token)
    }
    console.log(expense);
    console.log(header);
    console.log("Inside Expense Update(): Calling Delete REST API");
    return this.http.post( "http://localhost:8084/v1/api/grpExpense/settle", header);
  }
}
