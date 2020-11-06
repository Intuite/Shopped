import { Directive, ElementRef, Input, Renderer2 } from '@angular/core';

type ColorClasses = 'primary' | 'accent' | 'warn' | 'success' | 'danger' | undefined;

@Directive({
  selector: '[jhiMaterialColor]',
})
export class ColorDirectiveDirective {
  @Input() set jhiMaterialColor(value: ColorClasses) {
    this.renderer.addClass(this.element.nativeElement, `mat-${value}`);
  }

  constructor(private element: ElementRef, private renderer: Renderer2) {}
}
