import { Directive, ElementRef, Input, Renderer2 } from '@angular/core';

type ColorClasses = 'mat-primary' | 'mat-accent' | 'mat-warn' | 'mat-success' | 'mat-danger' | undefined;

@Directive({
  selector: '[jhiMaterialColor]',
})
export class ColorDirectiveDirective {
  @Input() set jhiMaterialColor(value: ColorClasses) {
    this.renderer.addClass(this.element.nativeElement, `mat-${value}`);
  }

  constructor(private element: ElementRef, private renderer: Renderer2) {}
}
