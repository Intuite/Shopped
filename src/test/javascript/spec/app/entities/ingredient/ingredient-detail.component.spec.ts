import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ShoppedTestModule } from '../../../test.module';
import { IngredientDetailComponent } from 'app/entities/ingredient/ingredient-detail.component';
import { Ingredient } from 'app/shared/model/ingredient.model';

describe('Component Tests', () => {
  describe('Ingredient Management Detail Component', () => {
    let comp: IngredientDetailComponent;
    let fixture: ComponentFixture<IngredientDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ ingredient: new Ingredient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [IngredientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IngredientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngredientDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load ingredient on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ingredient).toEqual(jasmine.objectContaining({ id: 123 }));
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
