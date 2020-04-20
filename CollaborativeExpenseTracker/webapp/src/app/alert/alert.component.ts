import { Component, OnInit } from "@angular/core";
import { AlertService } from "../services/alert.service";

@Component({
  selector: "app-alert",
  templateUrl: "./alert.component.html",
  styleUrls: ["./alert.component.scss"],
})
export class AlertComponent implements OnInit {
  message: any;
  displayMessage: string;

  constructor(private alertService: AlertService) {}

  ngOnInit() {
    this.alertService.getMessage().subscribe((message) => {
      this.message = message;
      if(this.message){
        this.displayMessage = message.text;
        console.log(message.text);
      }
    });
  }
}
