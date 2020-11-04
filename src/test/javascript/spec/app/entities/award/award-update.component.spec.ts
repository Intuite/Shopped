import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { AwardUpdateComponent } from 'app/entities/award/award-update.component';
import { AwardService } from 'app/entities/award/award.service';
import { Award } from 'app/shared/model/award.model';

describe('Component Tests', () => {
  describe('Award Management Update Component', () => {
    let comp: AwardUpdateComponent;
    let fixture: ComponentFixture<AwardUpdateComponent>;
    let service: AwardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [AwardUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AwardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AwardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AwardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Award(123);
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
        const entity = new Award();
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
