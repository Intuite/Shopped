import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CartHasIngredientDetailComponent } from 'app/entities/cart-has-ingredient/cart-has-ingredient-detail.component';
import { CartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';

describe('Component Tests', () => {
  describe('CartHasIngredient Management Detail Component', () => {
    let comp: CartHasIngredientDetailComponent;
    let fixture: ComponentFixture<CartHasIngredientDetailComponent>;
    const route = ({ data: of({ cartHasIngredient: new CartHasIngredient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CartHasIngredientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CartHasIngredientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartHasIngredientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cartHasIngredient on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cartHasIngredient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
