import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { IngredientHasIngredientTagUpdateComponent } from 'app/entities/ingredient-has-ingredient-tag/ingredient-has-ingredient-tag-update.component';
import { IngredientHasIngredientTagService } from 'app/entities/ingredient-has-ingredient-tag/ingredient-has-ingredient-tag.service';
import { IngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';

describe('Component Tests', () => {
  describe('IngredientHasIngredientTag Management Update Component', () => {
    let comp: IngredientHasIngredientTagUpdateComponent;
    let fixture: ComponentFixture<IngredientHasIngredientTagUpdateComponent>;
    let service: IngredientHasIngredientTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [IngredientHasIngredientTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IngredientHasIngredientTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngredientHasIngredientTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngredientHasIngredientTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IngredientHasIngredientTag(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new IngredientHasIngredientTag();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
