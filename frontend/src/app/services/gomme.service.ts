import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Gomma } from '../models/gomma';

@Injectable({ providedIn: 'root' })
export class GommeService {

  private http = inject(HttpClient);
  private url = 'http://localhost:8080/api/gomme';

  findAll(): Observable<Gomma[]> {
    return this.http.get<Gomma[]>(this.url);
  }

  findByTarga(targa: string): Observable<Gomma[]> {
    return this.http.get<Gomma[]>(`${this.url}/targa/${targa}`);
  }
}
