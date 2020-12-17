import { Pipe } from '@angular/core';

@Pipe({
  name: 'reverse',
})
export class ReversePipe {
  transform(items: any[]): any[] {
    return items.reverse();
  }
}
