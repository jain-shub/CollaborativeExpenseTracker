import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteGroupExpenseComponent } from './delete-group-expense.component';

describe('DeleteGroupExpenseComponent', () => {
  let component: DeleteGroupExpenseComponent;
  let fixture: ComponentFixture<DeleteGroupExpenseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteGroupExpenseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteGroupExpenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
