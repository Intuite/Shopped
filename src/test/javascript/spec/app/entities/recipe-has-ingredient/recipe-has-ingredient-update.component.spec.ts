import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeHasIngredientUpdateComponent } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient-update.component';
import { RecipeHasIngredientService } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient.service';
import { RecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';

describe('Component Tests', () => {
  describe('RecipeHasIngredient Management Update Component', () => {
    let comp: RecipeHasIngredientUpdateComponent;
    let fixture: ComponentFixture<RecipeHasIngredientUpdateComponent>;
    let service: RecipeHasIngredientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeHasIngredientUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecipeHasIngredientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecipeHasIngredientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecipeHasIngredientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecipeHasIngredient(123);
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
        const entity = new RecipeHasIngredient();
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
