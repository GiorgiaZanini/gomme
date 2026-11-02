import {Component, inject, OnInit, signal} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FiltriRicerca} from './component/filtri-ricerca/filtri-ricerca';
import {Tabella} from './component/tabella/tabella';
import {GommeService} from './services/gomme.service';
import {Gomma} from './models/gomma';

@Component({
  imports: [RouterOutlet, FiltriRicerca, Tabella],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App implements OnInit {
  private gommeService = inject(GommeService);
  gomme = signal<Gomma[]>([]);

  ngOnInit() {
    this.gommeService.findAll().subscribe(risultato => this.gomme.set(risultato));
  }
}
