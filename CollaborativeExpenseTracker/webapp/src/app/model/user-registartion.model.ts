import { Expense } from './expense.model';

export class UserRegistration {
  firstName: string;
  lastName: string;
  email: string;
  dueExpense: Expense;
  owedExpense: Expense;
  amountDue: number;
  amountOwed: number;
  password: string;
  createdDate: Date;
  isVerified: boolean;

  constructor() {
    this.createdDate = new Date();
    this.isVerified = false;
    this.amountDue = 0;
    this.amountOwed = 0;
    this.dueExpense = new Expense();
    this.owedExpense = new Expense();
  }
}
