import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BuyCookieComponent } from 'app/shared/components/buy-cookie/buy-cookie.component';

describe('BuyCookieComponent', () => {
  let component: BuyCookieComponent;
  let fixture: ComponentFixture<BuyCookieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BuyCookieComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuyCookieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
