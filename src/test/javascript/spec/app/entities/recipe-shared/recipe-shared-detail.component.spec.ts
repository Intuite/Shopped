import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeSharedDetailComponent } from 'app/entities/recipe-shared/recipe-shared-detail.component';
import { RecipeShared } from 'app/shared/model/recipe-shared.model';

describe('Component Tests', () => {
  describe('RecipeShared Management Detail Component', () => {
    let comp: RecipeSharedDetailComponent;
    let fixture: ComponentFixture<RecipeSharedDetailComponent>;
    const route = ({ data: of({ recipeShared: new RecipeShared(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeSharedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecipeSharedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecipeSharedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recipeShared on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recipeShared).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
