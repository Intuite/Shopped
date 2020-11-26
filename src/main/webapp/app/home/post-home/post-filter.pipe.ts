import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'postFilter',
})
export class PostFilterPipe implements PipeTransform {
  transform(items: any[], searchText: string): any[] {
    if (!items) return [];
    if (!searchText) return items;
    return items
      .filter(x => {
        return JSON.stringify(x)
          .toLowerCase()
          .replace(/(\{|,)\s*(.+?)\s*:/g, '')
          .includes(searchText.toLowerCase());
      })
      .sort((a: any, b: any) => {
        const x = a.name.toUpperCase(),
          y = b.name.toUpperCase();
        return x === y ? 0 : x > y ? 1 : -1;
      });
  }
}
