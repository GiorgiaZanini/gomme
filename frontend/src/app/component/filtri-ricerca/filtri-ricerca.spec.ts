import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FiltriRicerca } from './filtri-ricerca';

describe('FiltriRicerca', () => {
  let component: FiltriRicerca;
  let fixture: ComponentFixture<FiltriRicerca>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FiltriRicerca],
    }).compileComponents();

    fixture = TestBed.createComponent(FiltriRicerca);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
