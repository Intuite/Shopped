import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CollectionHasRecipeDetailComponent } from 'app/entities/collection-has-recipe/collection-has-recipe-detail.component';
import { CollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';

describe('Component Tests', () => {
  describe('CollectionHasRecipe Management Detail Component', () => {
    let comp: CollectionHasRecipeDetailComponent;
    let fixture: ComponentFixture<CollectionHasRecipeDetailComponent>;
    const route = ({ data: of({ collectionHasRecipe: new CollectionHasRecipe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CollectionHasRecipeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CollectionHasRecipeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollectionHasRecipeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load collectionHasRecipe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collectionHasRecipe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
