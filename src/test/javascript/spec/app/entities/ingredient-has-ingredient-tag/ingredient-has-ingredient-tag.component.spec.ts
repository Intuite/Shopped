import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ShoppedTestModule } from '../../../test.module';
import { IngredientHasIngredientTagComponent } from 'app/entities/ingredient-has-ingredient-tag/ingredient-has-ingredient-tag.component';
import { IngredientHasIngredientTagService } from 'app/entities/ingredient-has-ingredient-tag/ingredient-has-ingredient-tag.service';
import { IngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';

describe('Component Tests', () => {
  describe('IngredientHasIngredientTag Management Component', () => {
    let comp: IngredientHasIngredientTagComponent;
    let fixture: ComponentFixture<IngredientHasIngredientTagComponent>;
    let service: IngredientHasIngredientTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [IngredientHasIngredientTagComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(IngredientHasIngredientTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngredientHasIngredientTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngredientHasIngredientTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IngredientHasIngredientTag(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ingredientHasIngredientTags && comp.ingredientHasIngredientTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IngredientHasIngredientTag(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ingredientHasIngredientTags && comp.ingredientHasIngredientTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
