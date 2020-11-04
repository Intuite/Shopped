import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { IngredientTagUpdateComponent } from 'app/entities/ingredient-tag/ingredient-tag-update.component';
import { IngredientTagService } from 'app/entities/ingredient-tag/ingredient-tag.service';
import { IngredientTag } from 'app/shared/model/ingredient-tag.model';

describe('Component Tests', () => {
  describe('IngredientTag Management Update Component', () => {
    let comp: IngredientTagUpdateComponent;
    let fixture: ComponentFixture<IngredientTagUpdateComponent>;
    let service: IngredientTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [IngredientTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IngredientTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngredientTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngredientTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IngredientTag(123);
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
        const entity = new IngredientTag();
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
