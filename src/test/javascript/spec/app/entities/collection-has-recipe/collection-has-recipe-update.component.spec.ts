import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CollectionHasRecipeUpdateComponent } from 'app/entities/collection-has-recipe/collection-has-recipe-update.component';
import { CollectionHasRecipeService } from 'app/entities/collection-has-recipe/collection-has-recipe.service';
import { CollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';

describe('Component Tests', () => {
  describe('CollectionHasRecipe Management Update Component', () => {
    let comp: CollectionHasRecipeUpdateComponent;
    let fixture: ComponentFixture<CollectionHasRecipeUpdateComponent>;
    let service: CollectionHasRecipeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CollectionHasRecipeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CollectionHasRecipeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectionHasRecipeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionHasRecipeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CollectionHasRecipe(123);
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
        const entity = new CollectionHasRecipe();
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
