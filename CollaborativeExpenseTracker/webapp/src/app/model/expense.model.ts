export class Expense {
  id: string ="";
  // toEmail: string; //The user whom the money has to be paid
  fromUserName: string = "";
  toUserName: string;
  name: string;
  amount: string;
  type: string; //enum: ["FULL", "SPLIT", "CUSTOM"];
  fromUserSplitPercentageValue: string;
  toUserSplitPercentageValue: string;

  constructor() {}
}
