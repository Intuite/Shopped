import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CartHasIngredientUpdateComponent } from 'app/entities/cart-has-ingredient/cart-has-ingredient-update.component';
import { CartHasIngredientService } from 'app/entities/cart-has-ingredient/cart-has-ingredient.service';
import { CartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';

describe('Component Tests', () => {
  describe('CartHasIngredient Management Update Component', () => {
    let comp: CartHasIngredientUpdateComponent;
    let fixture: ComponentFixture<CartHasIngredientUpdateComponent>;
    let service: CartHasIngredientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CartHasIngredientUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CartHasIngredientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartHasIngredientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartHasIngredientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CartHasIngredient(123);
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
        const entity = new CartHasIngredient();
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
