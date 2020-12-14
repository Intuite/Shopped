import { Injectable, EventEmitter } from '@angular/core';
import { Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EventEmitterServiceService {
  invokeFunction = new EventEmitter();
  subsVar: Subscription | undefined;

  constructor() {}

  onAwardUpdated(): void {
    this.invokeFunction.emit();
  }
}
