import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CollectionUpdateComponent } from 'app/entities/collection/collection-update.component';
import { CollectionService } from 'app/entities/collection/collection.service';
import { Collection } from 'app/shared/model/collection.model';

describe('Component Tests', () => {
  describe('Collection Management Update Component', () => {
    let comp: CollectionUpdateComponent;
    let fixture: ComponentFixture<CollectionUpdateComponent>;
    let service: CollectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CollectionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CollectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Collection(123);
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
        const entity = new Collection();
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
