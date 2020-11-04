import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeHasIngredientDetailComponent } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient-detail.component';
import { RecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';

describe('Component Tests', () => {
  describe('RecipeHasIngredient Management Detail Component', () => {
    let comp: RecipeHasIngredientDetailComponent;
    let fixture: ComponentFixture<RecipeHasIngredientDetailComponent>;
    const route = ({ data: of({ recipeHasIngredient: new RecipeHasIngredient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeHasIngredientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecipeHasIngredientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecipeHasIngredientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recipeHasIngredient on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recipeHasIngredient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
