import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeHasRecipeTagUpdateComponent } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag-update.component';
import { RecipeHasRecipeTagService } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag.service';
import { RecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';

describe('Component Tests', () => {
  describe('RecipeHasRecipeTag Management Update Component', () => {
    let comp: RecipeHasRecipeTagUpdateComponent;
    let fixture: ComponentFixture<RecipeHasRecipeTagUpdateComponent>;
    let service: RecipeHasRecipeTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeHasRecipeTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecipeHasRecipeTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecipeHasRecipeTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecipeHasRecipeTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecipeHasRecipeTag(123);
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
        const entity = new RecipeHasRecipeTag();
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
