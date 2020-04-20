import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpHeaders,
} from "@angular/common/http";
import { Group } from "../model/group.model";
import { Subject } from "rxjs";
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: "root",
})
export class GroupService {
  private _refreshNeeded$ = new Subject<void>();

  get refreshNeeded$() {
    return this._refreshNeeded$;
  }

  constructor(private http: HttpClient) {}

  public addGroup(name: string, memberEmail: string[]) {
    const groupType = "DEFAULT";
    const group: Group = {
      groupId: "",
      name: name,
      groupType: "DEFAULT",
      balance: 0,
      createdBy: JSON.stringify(JSON.parse(localStorage.getItem("currentUser")).user),
      memberEmail: memberEmail,
      memberEmailString: JSON.stringify(memberEmail),
      members: [],
    };
    const token = localStorage.getItem("token");
    var header = {
      headers: new HttpHeaders().set("Authorization", `Bearer ` + token),
    };
    return this.http.post(
      "http://localhost:8084/api/groups/",
      JSON.stringify(group),
      header
    ).pipe(tap(()=>{
      this._refreshNeeded$.next();
    }));
  }

  public updateGroup(group:Group) {
    const groupId = localStorage.getItem("currentGroupId");
    const token = localStorage.getItem("token");
    var header = {
      headers: new HttpHeaders().set("Authorization", `Bearer ` + token),
    };
    return this.http.post<Group>("http://localhost:8084/api/groups/"+groupId, group, header);
  }

  public getGroupDetails() {
    const token = localStorage.getItem("token");
    var header = {
      headers: new HttpHeaders().set("Authorization", `Bearer ` + token),
    };
    return this.http.get<Group[]>("http://localhost:8084/api/groups/", header);
  }

  public getGroupById() {
    const groupId = localStorage.getItem("currentGroupId");
    console.log(groupId);

    const token = localStorage.getItem("token");
    var header = {
      headers: new HttpHeaders().set("Authorization", `Bearer ` + token),
    };
    return this.http.get<Group>(
      "http://localhost:8084/api/groups/" + groupId,
      header
    );
  }
}
