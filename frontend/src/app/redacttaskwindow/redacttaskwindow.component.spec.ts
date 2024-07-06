import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RedacttaskwindowComponent } from './redacttaskwindow.component';

describe('RedacttaskwindowComponent', () => {
  let component: RedacttaskwindowComponent;
  let fixture: ComponentFixture<RedacttaskwindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RedacttaskwindowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RedacttaskwindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
