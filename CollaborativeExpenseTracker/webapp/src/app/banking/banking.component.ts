import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AccountService } from "../services/account.service";
import { Observable } from "rxjs";
import { Account } from "../model/account-model";

@Component({
  selector: "app-banking",
  templateUrl: "./banking.component.html",
  styleUrls: ["./banking.component.scss"],
})
export class BankingComponent implements OnInit {
  constructor(private router: Router, private accountService: AccountService) {}

  onPlaidSuccess(event) {
    let onSuccessData = event;
    let publicToken = onSuccessData.token;
    let institution = onSuccessData.metadata.institution;
    console.log(event);
    let newAccountCreated$: Observable<Account> = this.accountService.createAccount(
      publicToken,
      institution
    );
    newAccountCreated$.subscribe((account) => {
      console.log(account);
    });
  }

  onPlaidExit(event) {
    // Get errors or exit reason.
  }

  onPlaidEvent(event) {}

  onPlaidLoad(event) {
    // Do something when the iframe loads.
  }

  onPlaidClick(event) {
    // Do something when the button is clicked.
  }

  exit() {
    this.router.navigateByUrl("/userPages");
  }

  ngOnInit(): void {}
}
