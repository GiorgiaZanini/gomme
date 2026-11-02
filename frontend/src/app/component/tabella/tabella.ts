import {Component, computed, input} from '@angular/core';
import {Gomma} from '../../models/gomma';
import {AREE, EDIFICI, STAGIONI} from '../../models/etichette';

@Component({
  imports: [],
  selector: 'app-tabella',
  styleUrl: './tabella.css',
  templateUrl: './tabella.html',
})
export class Tabella {
  protected readonly stagioni = STAGIONI;
  protected readonly edifici = EDIFICI;
  protected readonly aree = AREE;

  gomme = input<Gomma[]>([]);

  gommeOrdinate = computed(() =>
    [...this.gomme()].sort((a, b) => a.targa.localeCompare(b.targa))
  );
}
