import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeHasRecipeTagDetailComponent } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag-detail.component';
import { RecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';

describe('Component Tests', () => {
  describe('RecipeHasRecipeTag Management Detail Component', () => {
    let comp: RecipeHasRecipeTagDetailComponent;
    let fixture: ComponentFixture<RecipeHasRecipeTagDetailComponent>;
    const route = ({ data: of({ recipeHasRecipeTag: new RecipeHasRecipeTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeHasRecipeTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecipeHasRecipeTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecipeHasRecipeTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recipeHasRecipeTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recipeHasRecipeTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
