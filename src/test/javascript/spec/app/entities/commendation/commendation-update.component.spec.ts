import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CommendationUpdateComponent } from 'app/entities/commendation/commendation-update.component';
import { CommendationService } from 'app/entities/commendation/commendation.service';
import { Commendation } from 'app/shared/model/commendation.model';

describe('Component Tests', () => {
  describe('Commendation Management Update Component', () => {
    let comp: CommendationUpdateComponent;
    let fixture: ComponentFixture<CommendationUpdateComponent>;
    let service: CommendationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CommendationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommendationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommendationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommendationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Commendation(123);
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
        const entity = new Commendation();
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
