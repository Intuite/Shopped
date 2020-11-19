import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tagFilter',
})
export class TagFilterPipe implements PipeTransform {
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
      .sort((a, b) => (a.typeId > b.typeId ? 1 : -1));
  }
}
