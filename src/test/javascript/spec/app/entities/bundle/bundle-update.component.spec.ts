import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { BundleUpdateComponent } from 'app/entities/bundle/bundle-update.component';
import { BundleService } from 'app/entities/bundle/bundle.service';
import { Bundle } from 'app/shared/model/bundle.model';

describe('Component Tests', () => {
  describe('Bundle Management Update Component', () => {
    let comp: BundleUpdateComponent;
    let fixture: ComponentFixture<BundleUpdateComponent>;
    let service: BundleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [BundleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BundleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BundleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BundleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bundle(123);
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
        const entity = new Bundle();
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
