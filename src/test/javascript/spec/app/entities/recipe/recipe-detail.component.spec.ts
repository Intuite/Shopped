import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeDetailComponent } from 'app/entities/recipe/recipe-detail.component';
import { Recipe } from 'app/shared/model/recipe.model';

describe('Component Tests', () => {
  describe('Recipe Management Detail Component', () => {
    let comp: RecipeDetailComponent;
    let fixture: ComponentFixture<RecipeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ recipe: new Recipe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecipeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecipeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load recipe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recipe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
