import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Tabella } from './tabella';

describe('Tabella', () => {
  let component: Tabella;
  let fixture: ComponentFixture<Tabella>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Tabella],
    }).compileComponents();

    fixture = TestBed.createComponent(Tabella);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
