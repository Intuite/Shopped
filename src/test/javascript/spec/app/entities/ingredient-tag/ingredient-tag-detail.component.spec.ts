import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { IngredientTagDetailComponent } from 'app/entities/ingredient-tag/ingredient-tag-detail.component';
import { IngredientTag } from 'app/shared/model/ingredient-tag.model';

describe('Component Tests', () => {
  describe('IngredientTag Management Detail Component', () => {
    let comp: IngredientTagDetailComponent;
    let fixture: ComponentFixture<IngredientTagDetailComponent>;
    const route = ({ data: of({ ingredientTag: new IngredientTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [IngredientTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IngredientTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngredientTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ingredientTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ingredientTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
