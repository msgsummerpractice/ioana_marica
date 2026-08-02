import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'mask' })
export class MaskPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    return value[0] + '*'.repeat(value.length - 1);
  }
}
