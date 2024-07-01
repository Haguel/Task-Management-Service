import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatetaskwindowComponent } from './createtaskwindow.component';

describe('CreatetaskwindowComponent', () => {
  let component: CreatetaskwindowComponent;
  let fixture: ComponentFixture<CreatetaskwindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatetaskwindowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreatetaskwindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
