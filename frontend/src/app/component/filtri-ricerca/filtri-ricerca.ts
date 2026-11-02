import {Component, output} from '@angular/core';

@Component({
  imports: [],
  selector: 'app-filtri-ricerca',
  styleUrl: './filtri-ricerca.css',
  templateUrl: './filtri-ricerca.html',
})
export class FiltriRicerca {
  annulla = output<void>();
}
