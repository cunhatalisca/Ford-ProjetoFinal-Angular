import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Purchase, PurchaseRequest } from '../models/purchase.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PurchaseService {
  private readonly apiUrl = `${environment.apiUrl}/purchases`;
  private http = inject(HttpClient);

  savePurchase(purchase: PurchaseRequest): Observable<Purchase> {
    return this.http.post<Purchase>(this.apiUrl, purchase);
  }

  getAllPurchases(): Observable<Purchase[]> {
    return this.http.get<Purchase[]>(this.apiUrl);
  }

  updatePurchase(purchase: Purchase): Observable<Purchase> {
    return this.http.put<Purchase>(`${this.apiUrl}/${purchase.id}/status`, {
      status: purchase.status,
    });
  }
}
