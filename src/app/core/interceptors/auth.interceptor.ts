import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { StorageService } from '../services/storage.service';
import { environment } from '../../../environments/environment';

/**
 * Anexa o cabecalho Authorization: Bearer <token> nas requisicoes destinadas
 * a nossa API (environment.apiUrl). Chamadas externas (ViaCEP, Gemini) ficam intactas.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const storage = inject(StorageService);
  const token = storage.getItem<string>('authToken');

  if (token && req.url.startsWith(environment.apiUrl)) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
  }

  return next(req);
};
