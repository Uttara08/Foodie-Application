import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFavComponent } from './view-fav.component';

describe('ViewFavComponent', () => {
  let component: ViewFavComponent;
  let fixture: ComponentFixture<ViewFavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewFavComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewFavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
