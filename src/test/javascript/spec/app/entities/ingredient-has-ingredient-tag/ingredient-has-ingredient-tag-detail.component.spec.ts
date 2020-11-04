import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { IngredientHasIngredientTagDetailComponent } from 'app/entities/ingredient-has-ingredient-tag/ingredient-has-ingredient-tag-detail.component';
import { IngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';

describe('Component Tests', () => {
  describe('IngredientHasIngredientTag Management Detail Component', () => {
    let comp: IngredientHasIngredientTagDetailComponent;
    let fixture: ComponentFixture<IngredientHasIngredientTagDetailComponent>;
    const route = ({ data: of({ ingredientHasIngredientTag: new IngredientHasIngredientTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [IngredientHasIngredientTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IngredientHasIngredientTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngredientHasIngredientTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ingredientHasIngredientTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ingredientHasIngredientTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
