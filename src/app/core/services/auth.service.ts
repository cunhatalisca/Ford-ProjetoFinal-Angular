import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user.model';
import { StorageService } from './storage.service';
import { Role } from '../models/role.model';
import { environment } from '../../../environments/environment';

export interface AuthResponse {
  token: string;
  user: User;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = `${environment.apiUrl}/auth`;
  private readonly userKey = 'currentUser';
  private readonly tokenKey = 'authToken';

  private http = inject(HttpClient);
  private storageService = inject(StorageService);

  register(user: Omit<User, 'role' | 'id'>): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, user);
  }

  login(
    credentials: Pick<User, 'email' | 'password'>
  ): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap((response) => {
          if (response?.token) {
            this.storageService.setItem(this.tokenKey, response.token);
            this.storageService.setItem(this.userKey, response.user);
          }
        })
      );
  }

  logout(): void {
    this.storageService.removeItem(this.userKey);
    this.storageService.removeItem(this.tokenKey);
  }

  getCurrentUser(): User | null {
    return this.storageService.getItem<User>(this.userKey);
  }

  getToken(): string | null {
    return this.storageService.getItem<string>(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return this.getCurrentUser() !== null;
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user ? user.role === 'admin' : false;
  }

  hasRole(role: Role): boolean {
    const user = this.getCurrentUser();
    return user ? user.role === role : false;
  }
}
