import { Pipe } from '@angular/core';

@Pipe({
  name: 'recipeReverse',
})
export class RecipeReversePipe {
  transform(items: any[]): any[] {
    return items.reverse();
  }
}
