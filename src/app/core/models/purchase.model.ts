import { User } from './user.model';
import { Cars } from './cars.model';
import { Cores } from './cores.model';
import { PaymentData } from '../services/payment.service';

export interface Purchase {
  id?: string;
  user: User;
  car: Cars;
  selectedColor: Cores;
  payment: PaymentData;
  purchaseDate: Date;
  status: 'pendente' | 'aprovado' | 'rejeitado';
}

/**
 * Payload enxuto enviado ao backend ao concluir uma compra.
 * O usuario eh identificado pelo token JWT; o estoque eh debitado no servidor.
 */
export interface PurchaseRequest {
  carId: number | string;
  selectedColor: Cores;
  payment: PaymentData;
}
