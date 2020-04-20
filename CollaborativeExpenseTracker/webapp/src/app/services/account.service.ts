import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Subject } from "rxjs";
import { Account } from "../model/account-model";
import { environment } from "./../../environments/environment";

/**
 * Service class for the Account model. Works with the Banking Component.
 */
@Injectable({
  providedIn: "root",
})
export class AccountService {
  private token: string = localStorage.getItem("token");
  accountResource: string;
  accountResourceURL: string;
  newAccountSubject: Subject<Account>;

  /**
   * Constructor.
   */
  constructor(private http: HttpClient) {
    this.accountResource = "v1/account";
    this.accountResourceURL = `${environment.serverBaseURL}/${this.accountResource}`;
    this.newAccountSubject = new Subject<Account>();
  }

  /**
   * Creates new Account once the user has authenticated with the Plaid Service.
   *
   * @param {Account} account: account {new Account object}
   * @return {Observable<Account>} {Observable for saved account object}
   */
  createAccount(
    publicToken: string,
    institution: { name: string; institution_id: string }
  ): Observable<Account> {
    let newAccount = new Account();
    newAccount.publicToken = publicToken;
    newAccount.institution = institution;
    console.log(newAccount);
    let header = {
      headers: new HttpHeaders().set("Authorization", `Bearer ` + this.token),
    };
    return this.http.post<Account>(this.accountResourceURL, newAccount, header);
  }
}
