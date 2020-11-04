import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeTagDetailComponent } from 'app/entities/recipe-tag/recipe-tag-detail.component';
import { RecipeTag } from 'app/shared/model/recipe-tag.model';

describe('Component Tests', () => {
  describe('RecipeTag Management Detail Component', () => {
    let comp: RecipeTagDetailComponent;
    let fixture: ComponentFixture<RecipeTagDetailComponent>;
    const route = ({ data: of({ recipeTag: new RecipeTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecipeTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecipeTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recipeTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recipeTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
