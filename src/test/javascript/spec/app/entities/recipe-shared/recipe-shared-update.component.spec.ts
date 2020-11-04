import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { RecipeSharedUpdateComponent } from 'app/entities/recipe-shared/recipe-shared-update.component';
import { RecipeSharedService } from 'app/entities/recipe-shared/recipe-shared.service';
import { RecipeShared } from 'app/shared/model/recipe-shared.model';

describe('Component Tests', () => {
  describe('RecipeShared Management Update Component', () => {
    let comp: RecipeSharedUpdateComponent;
    let fixture: ComponentFixture<RecipeSharedUpdateComponent>;
    let service: RecipeSharedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeSharedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecipeSharedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecipeSharedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecipeSharedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecipeShared(123);
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
        const entity = new RecipeShared();
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
