import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CartHasRecipeDetailComponent } from 'app/entities/cart-has-recipe/cart-has-recipe-detail.component';
import { CartHasRecipe } from 'app/shared/model/cart-has-recipe.model';

describe('Component Tests', () => {
  describe('CartHasRecipe Management Detail Component', () => {
    let comp: CartHasRecipeDetailComponent;
    let fixture: ComponentFixture<CartHasRecipeDetailComponent>;
    const route = ({ data: of({ cartHasRecipe: new CartHasRecipe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CartHasRecipeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CartHasRecipeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartHasRecipeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cartHasRecipe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cartHasRecipe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
