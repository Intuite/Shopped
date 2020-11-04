import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CartHasRecipeUpdateComponent } from 'app/entities/cart-has-recipe/cart-has-recipe-update.component';
import { CartHasRecipeService } from 'app/entities/cart-has-recipe/cart-has-recipe.service';
import { CartHasRecipe } from 'app/shared/model/cart-has-recipe.model';

describe('Component Tests', () => {
  describe('CartHasRecipe Management Update Component', () => {
    let comp: CartHasRecipeUpdateComponent;
    let fixture: ComponentFixture<CartHasRecipeUpdateComponent>;
    let service: CartHasRecipeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CartHasRecipeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CartHasRecipeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartHasRecipeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartHasRecipeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CartHasRecipe(123);
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
        const entity = new CartHasRecipe();
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
