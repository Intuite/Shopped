import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { BiteUpdateComponent } from 'app/entities/bite/bite-update.component';
import { BiteService } from 'app/entities/bite/bite.service';
import { Bite } from 'app/shared/model/bite.model';

describe('Component Tests', () => {
  describe('Bite Management Update Component', () => {
    let comp: BiteUpdateComponent;
    let fixture: ComponentFixture<BiteUpdateComponent>;
    let service: BiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [BiteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BiteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BiteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bite(123);
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
        const entity = new Bite();
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
