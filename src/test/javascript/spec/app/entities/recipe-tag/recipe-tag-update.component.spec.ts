import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeTagUpdateComponent } from 'app/entities/recipe-tag/recipe-tag-update.component';
import { RecipeTagService } from 'app/entities/recipe-tag/recipe-tag.service';
import { RecipeTag } from 'app/shared/model/recipe-tag.model';

describe('Component Tests', () => {
  describe('RecipeTag Management Update Component', () => {
    let comp: RecipeTagUpdateComponent;
    let fixture: ComponentFixture<RecipeTagUpdateComponent>;
    let service: RecipeTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecipeTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecipeTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecipeTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecipeTag(123);
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
        const entity = new RecipeTag();
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
